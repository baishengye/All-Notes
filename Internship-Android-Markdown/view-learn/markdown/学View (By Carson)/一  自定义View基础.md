本文章做学习记录之用，原文章信息如下:

>作者：Carson带你学安卓
>链接：https://www.jianshu.com/p/146e5cec4863
>来源：简书

## 一. 自定义View基础
![](https://s3.bmp.ovh/imgs/2022/07/15/d6cffca67888f154.webp)

### 1. 视图定义
即日常说的View，具体表现为显示在屏幕上的各种视图控件，如TextView、LinearLayout等。

### 2. 视图分类
视图View主要分为两类：
- 单一视图：即一个View、不包含子View，如TextView
- 视图组，即多个View组成的ViewGroup、包含子View，如LinearLayout

Android中的UI组件都由View、ViewGroup共同组成。

### 3. 视图类简介
- 视图的核心类是：View类
- View类是Android中各种组件的基类，如View是ViewGroup基类
- View的构造函数：共有4个，具体如下：

> 自定义View必须重写至少一个构造函数：

```java
// 构造函数1
// 调用场景：View是在Java代码里面new的
public CarsonView(Context context) {
    super(context);
}

// 构造函数2
// 调用场景：View是在.xml里声明的
// 自定义属性是从AttributeSet参数传进来的
public  CarsonView(Context context, AttributeSet attrs) {
    super(context, attrs);
}

// 构造函数3
// 应用场景：View有style属性时
// 一般是在第二个构造函数里主动调用；不会自动调用
public  CarsonView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
}

// 构造函数4
// 应用场景：View有style属性时、API21之后才使用
// 一般是在第二个构造函数里主动调用；不会自动调用
public  CarsonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
}
```
更加具体的使用请看：深入理解[View的构造函数](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0806/4575.html)和[理解View的构造函数](https://www.cnblogs.com/angeldevil/p/3479431.html#three)

### 4. 视图结构
对于包含子View的视图组（ViewGroup），结构是树形结构
ViewGroup下可能有多个ViewGroup或View，如下图：
![](https://upload-images.jianshu.io/upload_images/944365-4c99a1e4db841957.png?imageMogr2/auto-orient/strip|imageView2/2/w/686/format/webp)

这里需要特别注意的是：在View的绘制过程中，**永远都是从View树结构的根节点开始(即从树的顶端开始)，一层一层、一个个分支地自上而下遍历进行（即树形递归）**，最终计算整个View树中各个View，从而最终确定整个View树的相关属性。


### 5. Android坐标系
Android的坐标系定义为：

- 屏幕的左上角为坐标原点
- 向右为x轴增大方向
- 向下为y轴增大方向
具体如下图：
![](https://upload-images.jianshu.io/upload_images/944365-017b5dc710ab0470.png?imageMogr2/auto-orient/strip|imageView2/2/w/686/format/webp)

注：区别于一般的数学坐标系
![](https://upload-images.jianshu.io/upload_images/944365-bddc2366bca78eff.png?imageMogr2/auto-orient/strip|imageView2/2/w/619/format/webp)
$$两者坐标系的区别$$

### 6. View位置（坐标）描述
视图的位置由四个顶点决定，如图1-3所示的A、B、C、D。
![](https://upload-images.jianshu.io/upload_images/944365-1281053d1ac659ba.png?imageMogr2/auto-orient/strip|imageView2/2/w/686/format/webp)

视图的位置是相对于父控件而言的，四个顶点的位置描述分别由四个与父控件相关的值决定：

- 顶部(Top)：视图上边界到父控件上边界的距离；
- 左边(Left)：视图左边界到父控件左边界的距离；
- 右边(Right)：视图右边界到父控件左边界的距离；
- 底部(Bottom)：视图下边界到父控件上边界的距离。

具体如图1-4所示。
![](https://upload-images.jianshu.io/upload_images/944365-557d81e6b7926b6f.png?imageMogr2/auto-orient/strip|imageView2/2/w/686/format/webp)

可根据视图位置的左上顶点、右下顶点进行记忆：

- 顶部(Top)：视图左上顶点到父控件上边界的距离；
- 左边(Left)：视图左上顶点到父控件左边界的距离；
- 右边(Right)：视图右下顶点到父控件左边界的距离；
- 底部(Bottom)：视图右下顶点到父控件上边界的距离。


### 7. 位置获取方式
视图的位置获取是通过View.getXXX()方法进行获取。
```java
- 获取顶部距离(Top)：getTop()
- 获取左边距离(Left)：getLeft()
- 获取右边距离(Right)：getRight()
- 获取底部距离(Bottom)：getBottom()
```

与MotionEvent中 get()和getRaw()的区别
```java
//get() ：触摸点相对于其所在组件坐标系的坐标
 event.getX();       
 event.getY();

//getRaw() ：触摸点相对于屏幕默认坐标系的坐标
 event.getRawX();    
 event.getRawY();
```
具体如下图：
![](https://upload-images.jianshu.io/upload_images/944365-e50a2705cdd632d3.png?imageMogr2/auto-orient/strip|imageView2/2/w/331/format/webp)

$$get() 和 getRaw() 的区别$$


### 8. 角度（angle）& 弧度（radian）
自定义View实际上是将一些简单的形状通过计算，从而组合到一起形成的效果。

> 这会涉及到画布的相关操作(旋转)、正余弦函数计算等，即会涉及到角度(angle)与弧度(radian)的相关知识。

**角度和弧度都是描述角的一种度量单位，区别如下图**：
![](https://upload-images.jianshu.io/upload_images/944365-7a81d3e1715eda0b.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)
$$角度和弧度区别$$

**在默认的屏幕坐标系中角度增大方向为顺时针。**
![](https://upload-images.jianshu.io/upload_images/944365-de35d1bdfea46470.png?imageMogr2/auto-orient/strip|imageView2/2/w/318/format/webp)
$$屏幕坐标系角度增大方向$$

**注：在常见的数学坐标系中角度增大方向为逆时针**


### 9. 颜色相关
Android中的颜色相关内容包括颜色模式，创建颜色的方式，以及颜色的混合模式等。

#### 9.1 颜色模式
Android支持的颜色模式主要包括：

```java
ARGB8888：四通道高精度(32位)
ARGB4444：四通道低精度(16位)
RGB565：Android屏幕默认模式(16位)
Alpha8：仅有透明通道(8位)
```

这里需要特别注意的是：
```java
字母：表示通道类型；
数值：表示该类型用多少位二进制来描述；
示例说明：ARGB8888，表示有四个通道(ARGB)；每个对应的通道均用8位来描述。
```

**以ARGB8888为例介绍颜色定义**:
![](https://upload-images.jianshu.io/upload_images/944365-f63d3055739f08b2.png?imageMogr2/auto-orient/strip|imageView2/2/w/700/format/webp)
$$ARGB88888$$


#### 9.2 颜色定义
主要分为xml定义 / java定义。
```java
/**
  * 定义方式1：xml
  * 在/res/values/color.xml文件中定义
  */
  <?xml version="1.0" encoding="utf-8"?>
  <resources>
    //定义了红色（没有alpha（透明）通道）
    <color name="red">#ff0000</color>
    //定义了蓝色（没有alpha（透明）通道）
    <color name="green">#00ff00</color>
  </resources>

  // 在xml文件中以”#“开头定义颜色，后面跟十六进制的值，有如下几种定义方式：
  #f00  //低精度 - 不带透明通道红色      
  #af00 //低精度 - 带透明通道红色        
  #ff0000 //高精度 - 不带透明通道红色          
  #aaff0000 //高精度 - 带透明通道红色       
```

```java
/**
  * 定义方式2：Java
  */
  // 使用Color类定义颜色
  int color = Color.GRAY; //灰色

  // Color类使用ARGB值表示
  int color = Color.argb(127, 255, 0, 0); //半透明红色   
  int color = 0xaaff0000; //带有透明度的红色   
```


#### 9.3 颜色引用
主要分为xml定义 / java定义。
```java

/**
  * 引用方式1：xml
  */
  // 1. 在style文件中引用
  <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
       <item name="colorPrimary">@color/red</item>
  </style>
  // 2. 在layout文件中引用
  android:background="@color/red"     
  // 3. 在layout文件中创建并使用颜色
  android:background="#ff0000"       
```
```java
/**
  * 引用方式2：Java
  */
  //方法1
  int color = getResources().getColor(R.color.mycolor);

  //方法2（API 23及以上）
  int color = getColor(R.color.myColor);      
```