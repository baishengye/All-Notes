# 自定义控件
[![bBxH1A.md.png](https://s1.ax1x.com/2022/03/06/bBxH1A.md.png)](https://imgtu.com/i/bBxH1A)
```kotlin
我们使用的所有控件都是直接或间接继承自View的，
所有的布局都是直接或间接继承自ViewGroup的

View是Android中最基本的一种UI组件，它可以在屏幕上绘制一块矩形区域，并且能够响应这块区域的各种事件
因此我们使用的各种组件其实都是在View基础上添加了各自特有的功能
而ViewGroup是一种特殊的View，它可以包含很多子View和子ViewGroup，是一个放置控件和布局的容器
所以其实我们可以自己来创建一个自定义控件
```
## 引入布局
```kotlin
我们可以用两个Button和一个TextView来创建一个标题栏
一般一个Activity会有很多个标题栏，
如果每个Activity的布局中都编写一边一样的标题栏代码就很烦
所以我们会以引入布局的方式来解决这个问题
```
```xml
在title.xml中写一个标题栏，方便引用
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@drawable/title_bg">

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/titleBack"
        android:layout_gravity="center"
        android:layout_margin="5dp"
        android:background="@drawable/back_bg"
        android:text="Back"
        android:textColor="#fff"/>

    <TextView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:id="@+id/textView"
        android:layout_gravity="center"
        android:layout_weight="1"
        android:gravity="center"
        android:textColor="#fff"
        android:textSize="24sp"
        android:text="Title Text"/>

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/titleEdit"
        android:layout_gravity="center"
        android:text="Edit"
        android:layout_margin="5dp"
        android:background="@drawable/edit_bg"
        android:textColor="#fff"
        />

    <!--gravity是设置自身内部元素的对齐方式。
    比如一个TextView，则是设置内部文字的对齐方式。如果是ViewGroup组件如LinearLayout的话，则为设置它内部view组件的对齐方式。
    layout_gravity是设置自身相当于父容器的对齐方式。
    比如，一个TextView设置layout_gravity属性，则表示这TextView相对于父容器的对齐方式-->

    <!--layout_margin是指定控件在上下左右方向上的间距
    其他的有layout_marginLeft,layout_marginRight,layout_marginTop,layout_marginBottom,指明了具体的方向-->
</LinearLayout>

然后再activity_main.xml中引用
<include layout="@layout/title"/>
```
```kotlin
我们还需要在kt代码中隐藏系统自带的标题栏才能看到我们自己写的
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    supportActionBar?.hide()//把系统自带标题栏隐藏起来
}
```
## 创建自定义控件
```kotlin
此时引入布局的问题解决了，但是如果布局里面有些控件要求能够响应事件，我们难道要在每个Activity中都写一次注册事件的代码吗？
我们当然不能这么干。
在每个activity中这个按钮的功能都是一样的，所以我们应当使用自定义控件的方式解决
```
```xml
activity_main.xml中需要写到
<!--添加自定义的控件的时候需要指明控件的完整类名-->
<com.bo.a4_learnui_3_learncustomviews.TitleLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```
```kotlin
我们需要编写一个TitleLayout来继承LinearLayout，然后在引入TitleLayout布局的时候机会初始化出两个按钮

class TitleLayout (context: Context,attrs: AttributeSet):LinearLayout(context,attrs){
    //这里的构造函数接受了两个参数
    /*Context:上下文
    AttributeSet:系统属性的一个集合*/
    init {
        LayoutInflater.from(context).inflate(R.layout.title,this)
        //from()根据上下文构建出一个LayoutInflater对象
        // 然后调用inflate()动态加载一个布局文件
        //他有两个参数
        /*第一个:要加载的布局文件的id
        第二个:给加载好的布局再添加一个父布局*/

        titleBack.setOnClickListener{
            val activity=context as Activity //把context上下文转化成了一个Activity
            //as是kotlin中的强制类型转换符
            activity.finish()
        }

        titleEdit.setOnClickListener {
            Toast.makeText(context,"你点击了按钮",Toast.LENGTH_SHORT).show()
        }
    }
    //现在我们每当在一个布局中引入TitleLayout的时候，Back和Edit按钮都已经自动实现好了
}
```