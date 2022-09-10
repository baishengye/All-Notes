## Measure过程


对于测量我们来说几个知识点,了解这几个知识点，之后的实例分析你才看得懂。

### 1、MeasureSpec 的理解
对于View的测量，肯定会和`MeasureSpec`接触，`MeasureSpec`是两个单词组成，翻译过来“测量规格”或者“测量参数”，很多博客包括官方文档对他的说明基本都是“一个`MeasureSpec`封装了从父容器传递给子容器的布局要求”,这个`MeasureSpec` 封装的是父容器传递给子容器的布局要求，而不是父容器对子容器的布局要求，“传递” 两个字很重要，更精确的说法应该这个`MeasureSpec`是由父`View`的`MeasureSpec`和子`View`的`LayoutParams`通过简单的计算得出一个针对子View的测量要求，这个测量要求就是`MeasureSpec`。

大家都知道一个`MeasureSpec`是一个大小跟模式的组合值,`MeasureSpec`中的值是一个整型（32位）将`size`和`mode`打包成一个`Int`型，其中高两位是`mode`，后面30位存的是`size`，是为了减少对象的分配开支。`MeasureSpec` 类似于下图，只不过这边用的是十进制的数，而`MeasureSpec` 是二进制存储的。

![](https://upload-images.jianshu.io/upload_images/966283-c330852c971b02a8.png?imageMogr2/auto-orient/strip|imageView2/2/w/405/format/webp)

注：-1 代表的是`EXACTLY`，-2 是`AT_MOST`

#### `MeasureSpec`一共有三种模式

- **`UPSPECIFIED`** : 父容器对于子容器没有任何限制,子容器想要多大就多大
- **`EXACTLY`**: 父容器已经为子容器设置了尺寸,子容器应当服从这些边界,不论子容器想要多大的空间。
- **`AT_MOST`**：子容器可以是声明大小内的任意大小

很多文章都会把这三个模式说成这样，当然其实包括官方文档也是这样表达的，但是这样并不好理解。特别是如果把这三种模式又和`MATCH_PARENT`和`WRAP_CONTENT `联系到一起，很多人就懵逼了。如果从代码上来看`view.measure(int widthMeasureSpec, int heightMeasureSpec)` 的两个`MeasureSpec`是父类传递过来的，但并不是完全是父`View`的要求，而是父`View`的`MeasureSpec`和子`View`自己的`LayoutParams`共同决定的，而子`View`的`LayoutParams`其实就是我们在`xml`写的时候设置的`layout_width`和`layout_height` 转化而来的。我们先来看代码会清晰一些：

**父View的measure的过程会先测量子View，等子View测量结果出来后，再来测量自己，上面的measureChildWithMargins就是用来测量某个子View的，我们来分析是怎样测量的，具体看注释：**
```java
protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) { 

    // 子View的LayoutParams，你在xml的layout_width和layout_height,
    // layout_xxx的值最后都会封装到这个个LayoutParams。
    final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();   

    //根据父View的测量规格和父View自己的Padding，
    //还有子View的Margin和已经用掉的空间大小（widthUsed），就能算出子View的MeasureSpec,具体计算过程看getChildMeasureSpec方法。
    final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,            
    mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin + widthUsed, lp.width);    

    final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,           
    mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin  + heightUsed, lp.height);  

    //通过父View的MeasureSpec和子View的自己LayoutParams的计算，算出子View的MeasureSpec，然后父容器传递给子容器的
    // 然后让子View用这个MeasureSpec（一个测量要求，比如不能超过多大）去测量自己，如果子View是ViewGroup 那还会递归往下测量。
    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

}
```
```java
// spec参数   表示父View的MeasureSpec 
// padding参数    父View的Padding+子View的Margin，父View的大小减去这些边距，才能精确算出
//               子View的MeasureSpec的size
// childDimension参数  表示该子View内部LayoutParams属性的值（lp.width或者lp.height）
//                    可以是wrap_content、match_parent、一个精确指(an exactly size),  
public static int getChildMeasureSpec(int spec, int padding, int childDimension) {  
    int specMode = MeasureSpec.getMode(spec);  //获得父View的mode  
    int specSize = MeasureSpec.getSize(spec);  //获得父View的大小  

   //父View的大小-自己的Padding+子View的Margin，得到值才是子View的大小。
    int size = Math.max(0, specSize - padding);   

    int resultSize = 0;    //初始化值，最后通过这个两个值生成子View的MeasureSpec
    int resultMode = 0;    //初始化值，最后通过这个两个值生成子View的MeasureSpec

    switch (specMode) {  
    // Parent has imposed an exact size on us  
    //1、父View是EXACTLY的 ！  
    case MeasureSpec.EXACTLY:   
        //1.1、子View的width或height是个精确值 (an exactly size)  
        if (childDimension >= 0) {            
            resultSize = childDimension;         //size为精确值  
            resultMode = MeasureSpec.EXACTLY;    //mode为 EXACTLY 。  
        }   
        //1.2、子View的width或height为 MATCH_PARENT/FILL_PARENT   
        else if (childDimension == LayoutParams.MATCH_PARENT) {  
            // Child wants to be our size. So be it.  
            resultSize = size;                   //size为父视图大小  
            resultMode = MeasureSpec.EXACTLY;    //mode为 EXACTLY 。  
        }   
        //1.3、子View的width或height为 WRAP_CONTENT  
        else if (childDimension == LayoutParams.WRAP_CONTENT) {  
            // Child wants to determine its own size. It can't be  
            // bigger than us.  
            resultSize = size;                   //size为父视图大小  
            resultMode = MeasureSpec.AT_MOST;    //mode为AT_MOST 。  
        }  
        break;  

    // Parent has imposed a maximum size on us  
    //2、父View是AT_MOST的 ！      
    case MeasureSpec.AT_MOST:  
        //2.1、子View的width或height是个精确值 (an exactly size)  
        if (childDimension >= 0) {  
            // Child wants a specific size... so be it  
            resultSize = childDimension;        //size为精确值  
            resultMode = MeasureSpec.EXACTLY;   //mode为 EXACTLY 。  
        }  
        //2.2、子View的width或height为 MATCH_PARENT/FILL_PARENT  
        else if (childDimension == LayoutParams.MATCH_PARENT) {  
            // Child wants to be our size, but our size is not fixed.  
            // Constrain child to not be bigger than us.  
            resultSize = size;                  //size为父视图大小  
            resultMode = MeasureSpec.AT_MOST;   //mode为AT_MOST  
        }  
        //2.3、子View的width或height为 WRAP_CONTENT  
        else if (childDimension == LayoutParams.WRAP_CONTENT) {  
            // Child wants to determine its own size. It can't be  
            // bigger than us.  
            resultSize = size;                  //size为父视图大小  
            resultMode = MeasureSpec.AT_MOST;   //mode为AT_MOST  
        }  
        break;  

    // Parent asked to see how big we want to be  
    //3、父View是UNSPECIFIED的 ！  
    case MeasureSpec.UNSPECIFIED:  
        //3.1、子View的width或height是个精确值 (an exactly size)  
        if (childDimension >= 0) {  
            // Child wants a specific size... let him have it  
            resultSize = childDimension;        //size为精确值  
            resultMode = MeasureSpec.EXACTLY;   //mode为 EXACTLY  
        }  
        //3.2、子View的width或height为 MATCH_PARENT/FILL_PARENT  
        else if (childDimension == LayoutParams.MATCH_PARENT) {  
            // Child wants to be our size... find out how big it should  
            // be  
            resultSize = 0;                        //size为0！ ,其值未定  
            resultMode = MeasureSpec.UNSPECIFIED;  //mode为 UNSPECIFIED  
        }   
        //3.3、子View的width或height为 WRAP_CONTENT  
        else if (childDimension == LayoutParams.WRAP_CONTENT) {  
            // Child wants to determine its own size.... find out how  
            // big it should be  
            resultSize = 0;                        //size为0! ，其值未定  
            resultMode = MeasureSpec.UNSPECIFIED;  //mode为 UNSPECIFIED  
        }  
        break;  
    }  
    //根据上面逻辑条件获取的mode和size构建MeasureSpec对象。  
    return MeasureSpec.makeMeasureSpec(resultSize, resultMode);  
}    
```

**上面的代码有点多，希望你仔细看一些注释，代码写得很多，其实计算原理很简单：**
- >1、如果我们在`xml` 的`layout_width`或者`layout_height `把值都写死，那么上述的测量完全就不需要了，之所以要上面的这步测量，是因为 `match_parent` 就是充满父容器，`wrap_content` 就是自己多大就多大， 我们写代码的时候特别爽，我们编码方便的时候，google就要帮我们计算你`match_parent`的时候是多大，`wrap_content`的是多大，这个计算过程，就是计算出来的父`View`的`MeasureSpec`不断往子`View`传递，结合子`View`的`LayoutParams` 一起再算出子`View`的`MeasureSpec`，然后继续传给子`View`，不断计算每个`View`的`MeasureSpec`，子`View`有了`MeasureSpec`才能测量自己和自己的子`View`。

- >2、上述代码如果这么来理解就简单了
    **如果父View的MeasureSpec 是EXACTLY，说明父View的大小是确切的，（确切的意思很好理解，如果一个View的MeasureSpec 是EXACTLY，那么它的size 是多大，最后展示到屏幕就一定是那么大）。**
    - >1、如果子`View` 的`layout_xxxx`是`MATCH_PARENT`，父`View`的大小是确切，子`View`的大小又`MATCH_PARENT`（充满整个父View），那么子View的大小肯定是确切的，而且大小值就是父View的`size`。所以子View的`size`=父View的`size`，`mode=EXACTLY`

    - >2、如果子`View` 的`layout_xxxx`是`WRAP_CONTENT`，也就是子`View`的大小是根据自己的`content` 来决定的，但是子`View`的毕竟是子`View`，大小不能超过父`View`的大小，但是子`View`的是`WRAP_CONTENT`，我们还不知道具体子`View`的大小是多少，要等到`child.measure(childWidthMeasureSpec, childHeightMeasureSpec)` 调用的时候才去真正测量子View 自己`content`的大小（比如`TextView wrap_content` 的时候你要测量`TextView content` 的大小，也就是字符占用的大小，这个测量就是在`child.measure(childWidthMeasureSpec, childHeightMeasureSpec)`的时候，才能测出字符的大小，`MeasureSpec` 的意思就是假设你字符`100px`，但是`MeasureSpec` 要求最大的只能`50px`，这时候就要截掉了）。通过上述描述，子`View MeasureSpec mode`的应该是`AT_MOST`，而`size` 暂定父`View`的 `size`，表示的意思就是子View的大小没有不确切的值，子`View`的大小最大为父`View`的大小，不能超过父`View`的大小（这就是`AT_MOST` 的意思），然后这个`MeasureSpec` 作为子`View measure`方法 的参数，作为子`View`的大小的约束或者说是要求，有了这个`MeasureSpec`子`View`再实现自己的测量。

    - >3、如果如果子`View` 的`layout_xxxx`是确定的值（`200dp`），那么就更简单了，不管你父View的`mode`和`size`是什么，我都写死了就是`200dp`，那么控件最后展示就是就是`200dp`，不管我的父`View`有多大，也不管我自己的`content` 有多大，反正我就是这么大，所以这种情况`MeasureSpec` 的`mode = EXACTLY` 大小`size=你在layout_xxxx` 填的那个值。

    **如果父View的MeasureSpec 是AT_MOST，说明父View的大小是不确定，最大的大小是MeasureSpec 的size值，不能超过这个值。**
    - > 1、如果子`View` 的`layout_xxxx`是`MATCH_PARENT`，父`View`的大小是不确定（只知道最大只能多大），子`View`的大小`MATCH_PARENT`（充满整个父`View`），那么子`View`你即使充满父容器，你的大小也是不确定的，父`View`自己都确定不了自己的大小，你`MATCH_PARENT`你的大小肯定也不能确定的，所以子`View`的`mode=AT_MOST`，`size=父View的size`，也就是你在布局虽然写的是`MATCH_PARENT`，但是由于你的父容器自己的大小不确定，导致子`View`的大小也不确定，只知道最大就是父`View`的大小。

    - >2、如果子`View` 的`layout_xxxx`是`WRAP_CONTENT`，父`View`的大小是不确定（只知道最大只能多大），子`View`又是`WRAP_CONTENT`，那么在子View的`Content`没算出大小之前，子`View`的大小最大就是父`View`的大小，所以子`View MeasureSpec mode`的就是`AT_MOST`，而`size` 暂定父`View`的 `size`。

    - >3、如果子`View` 的`layout_xxxx`是确定的值（`200dp`），同上，写多少就是多少，改变不了的。

    **如果父View的MeasureSpec 是UNSPECIFIED(未指定),表示没有任何束缚和约束，不像AT_MOST表示最大只能多大，不也像EXACTLY表示父View确定的大小，子View可以得到任意想要的大小，不受约束**
    - >1、如果子`View` 的`layout_xxxx`是`MATCH_PARENT`，因为父`View`的`MeasureSpec`是`UNSPECIFIED`，父`View`自己的大小并没有任何约束和要求，那么对于子`View`来说无论是`WRAP_CONTENT`还是`MATCH_PARENT`，子View也是没有任何束缚的，想多大就多大，没有不能超过多少的要求，一旦没有任何要求和约束，`size`的值就没有任何意义了，所以**一般都直接设置成0**

    - >2、同上...

    - >3、如果子View 的`layout_xxxx`是确定的值（`200dp`），同上，写多少就是多少，改变不了的（记住，只有设置的确切的值，那么无论怎么测量，大小都是不变的，都是你写的那个值）

到此为止，你是否对`MeasureSpec` 和三种模式、还有`WRAP_CONTENT`和`MATCH_PARENT`有一定的了解了.

### 2、View的测量过程主要是在onMeasure()方法
打开`View`的源码，找到`measure`方法，这个方法代码不少，但是测量工作都是在`onMeasure()`做的，`measure`方法是`final`的所以这个方法也不可重写，如果想自定义`View`的测量，你应该去重写`onMeasure()`方法

```java
public final void measure(int widthMeasureSpec, int heightMeasureSpec) {
  ......
  onMeasure(widthMeasureSpec,heightMeasureSpec);
  .....
}
```

### 3、View的onMeasure 的默认实现
打开View.java 的源码来看下onMeasure的实现
```java
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {    
  setMeasuredDimension(
  getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),            
  getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
}
```

`View`的`onMeasure`方法默认实现很简单，就是调用`setMeasuredDimension()`，`setMeasuredDimension()`可以简单理解就是给`mMeasuredWidth`和`mMeasuredHeight`设值，如果这两个值一旦设置了，那么意味着对于这个`View`的测量结束了，这个`View`的宽高已经有测量的结果出来了。如果我们想设定某个`View`的高宽，完全可以直接通过`setMeasuredDimension（100，200）`来设置死它的高宽（不建议），但是`setMeasuredDimension`方法必须在`onMeasure`方法中调用，不然会抛异常。我们来看下对于View来说它的默认高宽是怎么获取的。

```java
//获取的是android:minHeight属性的值或者View背景图片的大小值
protected int getSuggestedMinimumWidth() { 
   return (mBackground == null) ? mMinWidth : max(mMinWidth, mBackground.getMinimumWidth()); 
} 
//@param size参数一般表示设置了android:minHeight属性或者该View背景图片的大小值  
public static int getDefaultSize(int size, int measureSpec) {    
   int result = size;    
   int specMode = MeasureSpec.getMode(measureSpec);    
   int specSize = MeasureSpec.getSize(measureSpec);    
   switch (specMode) {    
   case MeasureSpec.UNSPECIFIED:        //表示该View的大小父视图未定，设置为默认值 
     result = size;  
     break;    
   case MeasureSpec.AT_MOST:    
   case MeasureSpec.EXACTLY:        
     result = specSize;  
     break;   
 }    
return result;
}
```

`getDefaultSize`的第一个参数`size`等于`getSuggestedMinimumXXXX`返回的的值（建议的最小宽度和高度），而建议的最小宽度和高度都是由`View`的`Background`尺寸与通过设置`View`的`minXXX`属性共同决定的，这个`size`可以理解为`View`的默认长度，而第二个参数`measureSpec`，是父`View`传给自己的`MeasureSpec`,这个`measureSpec`是通过测量计算出来的，具体的计算测量过程前面在讲解`MeasureSpec`已经讲得比较清楚了（是有父`View`的`MeasureSpec`和子`View`自己的`LayoutParams` 共同决定的）只要这个测试的`mode`不是`UNSPECIFIED`（未确定的），那么默认的就会用这个测量的数值当做`View`的高度。

对于`View`默认是测量很简单，大部分情况就是拿计算出来的`MeasureSpec`的`size` 当做最终测量的大小。而对于其他的一些View的派生类，如`TextView`、`Button`、`ImageView`等，它们的`onMeasure`方法系统了都做了重写，不会这么简单直接拿 `MeasureSpec` 的`size`来当大小，而去会先去测量字符或者图片的高度等，然后拿到`View`本身`content`这个高度（字符高度等），如果`MeasureSpec`是`AT_MOST`，而且`View`本身`content`的高度不超出`MeasureSpec`的`size`，那么可以直接用`View`本身`content`的高度（字符高度等），而不是像`View.java` 直接用`MeasureSpec`的`size`做为`View`的大小。

### 4、ViewGroup的Measure过程
`ViewGroup` 类并没有实现`onMeasure`，我们知道测量过程其实都是在`onMeasure`方法里面做的，我们来看下`FrameLayout` 的`onMeasure` 方法,具体分析看注释哦。

```java
//FrameLayout 的测量
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
....
int maxHeight = 0;
int maxWidth = 0;
int childState = 0;
for (int i = 0; i < count; i++) {    
   final View child = getChildAt(i);    
   if (mMeasureAllChildren || child.getVisibility() != GONE) {   
    // 遍历自己的子View，只要不是GONE的都会参与测量，measureChildWithMargins方法在最上面
    // 的源码已经讲过了，如果忘了回头去看看，基本思想就是父View把自己的MeasureSpec 
    // 传给子View结合子View自己的LayoutParams 算出子View 的MeasureSpec，然后继续往下传，
    // 传递叶子节点，叶子节点没有子View，根据传下来的这个MeasureSpec测量自己就好了。
     measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);       
     final LayoutParams lp = (LayoutParams) child.getLayoutParams(); 
     maxWidth = Math.max(maxWidth, child.getMeasuredWidth() +  lp.leftMargin + lp.rightMargin);        
     maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);  
     ....
     ....
   }
}
.....
.....
//所有的孩子测量之后，经过一系类的计算之后通过setMeasuredDimension设置自己的宽高，
//对于FrameLayout 可能用最大的字View的大小，对于LinearLayout，可能是高度的累加，
//具体测量的原理去看看源码。总的来说，父View是等所有的子View测量结束之后，再来测量自己。
setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),        
resolveSizeAndState(maxHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
....
}
```


#### 补
**View的测量过程都是先根据LayoutParams设定大小后根据layout_weight分配剩余空间。
 因此一旦设置了这个值，那么所在View在绘制的时候就会执行两次Measure过程。**