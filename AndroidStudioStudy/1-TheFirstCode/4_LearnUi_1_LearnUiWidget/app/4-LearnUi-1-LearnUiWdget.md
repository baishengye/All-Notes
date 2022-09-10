# 了解程序界面相关
```kotlin
谷推出的一种新的布局方式ConstraintLayout,和传统布局方式不同的是他不太适合用xml来编写，更适合可视化编辑(拖放控件)
所以下面会用传统布局方式介绍，但是网络上有很多资源，可以搜
```

# 控件的使用方法
## `TextView`
```xml
示例:
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">


    <!--layout_width="match_parent":TextView的宽度和父亲一样宽
    layout_height="wrap_content":和文本一样高-->
    <TextView
        android:id="@+id/textView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="This is a TextView"/>

</LinearLayout>

但是此时运行之后发现虽然文本正常显示了，但是TextView看不出来适合屏幕一样宽的
这是因为TextView中的文字是自动居左上角对齐，虽然TextView的宽度充满了整个屏幕，
但是文字内容不够长，所以效果出不来

改成:
 <TextView
    android:id="@+id/textView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center"
    android:textColor="#4f0011"
    android:textSize="24sp"
    android:text="This is a TextView"/>
    <!--  android:gravity="center"   这个是指定文字居中对齐 -->
    <!--android:textColor="#4f0011" 指定文字的颜色
    android:textSize="24dp"   指定文字的大小
    但是值得注意的是文字大小要用sp作为单位，这样用户在系统中修改文字显示尺寸的时候，程序中的文字才会变化-->
现在TextView的宽度适合屏幕宽度一样的，而且文字颜色大小也变了
其他的风格需要就去查文档
```
## `Button`
```xml
<Button
    android:id="@+id/button"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textAllCaps="false"
    android:text="Button"/>
    <!--由于Android系统会觉得Button比较重要，所以它会把Button中的显示文本自动转化成大写
    不想要大写的话需要加上一句
    android:textAllCaps="false"      false是不转化,true是转化-->
```
```kotlin
两种方法注册一个监听按钮的点击事件
class MainActivity : AppCompatActivity() , View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //函数API方式注册一个监听按钮的点击事件
        /*button.setOnClickListener {
            Toast.makeText(this,"click button",Toast.LENGTH_SHORT).show()
        }*/
        button.setOnClickListener(this)
    }

    //通过实现接口的方式注册一个监听按钮的点击事件
    override fun onClick(v: View?) {
        when(v?.id){//传进来的View不是空的就进行判断View的id
            R.id.button->{//如果View的id表明他是一个Button的话就执行逻辑
                Toast.makeText(this,"click button",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
```
## `EditText`
```xml
EditText是程序中用于和用户进行交互的另一个重要条件
它允许用户在控件里面输入和编辑内容，并且可以在程序中对这些内容进行处理
比如:发短信,发QQ，发微信等等

<EditText
    android:id="@+id/editText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Type something here"
    android:maxLines="2"/>
    <!--
    我们可以在EditText中输入文本
    我们平时可以看到文本框里应该会有提示信息，输入的时候就消失了，其实这种功能android已经为我们集成了
    android:hint="Type something here"  加入这行就可以有完成上述功能,""里面的就是提示信息

    由于我们高度选的是和文本一样高,那么当输入内容过多的时候，界面就会很难看,android也为我们提供了解决方式
    android:maxLines="2"  指定了editView中最大只能为2行，超过两行就会变成文本可以向上滚动,文本框不会变化了
```
```kotlin
EditText和Button合作:
button.setOnClickListener {
    val inputData=editText.text.toString()//获取editText中的文字
    Toast.makeText(this,inputData,Toast.LENGTH_SHORT).show()//在Toast中显示
}
```

## `ImageView`
```kotlin
ImageView是用于在界面中展示图片的控件
可以是任意图片。图片通常是放在drawable开头的目录中
```
```xml
    <ImageView
        android:id="@+id/imageView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/img_1"/>
    <!--android:src="@drawable/img_1"导入资源-->
```
```kotlin
button.setOnClickListener {
    imageView.setImageResource(R.drawable.img_2)//代码中修改展示的图片资源
}
点击后图片就会变成另外一张
```

## `ProgressBar`
```kotlin
在页面上显示一个进度条，表示程序正在加载一些数据
```
```xml
<ProgressBar
    android:id="@+id/progress"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:visibility="visible"/>
    <!--会看到一个圆圈在加载-->
    <!--但是我们会看到他一直在上面，怎么让他消失?
    android:visibility可以指定他的状态:一共有三种
        visible:可见
        invisible:不可见，但会占用屏幕空间，相当于透明了
        gone:不可见，也不会占用屏幕空间，相当于隐藏到别处了-->
```
```kotlin
/*进度条可不可见
    不可见->可见
    可见->不可见*/
    button.setOnClickListener {
        if(progress.visibility!=View.GONE)
            progress.visibility=View.GONE
        else
            progress.visibility=View.VISIBLE
    }
```
```xml
想要把圆圈状的进度条变成水平状的
<ProgressBar
    android:id="@+id/progressBar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:visibility="visible"
    style="?android:attr/progressBarStyleHorizontal"
    android:max="100"/>
    <!--如果我们不想要是圆圈样式的进度条，而是水平的进度条，就可以加上这两行
    style="?android:attr/progressBarStyleHorizontal"   :  z指定进度条的风格是水平的
    android:max="100"   :     给水平进度条设置一个最大值
    我们可以在代码里动态的修改进度-->
```
```kotlin
button.setOnClickListener {
    progressBar.progress=progressBar.progress+10
}
每次点击按钮进度就会+10，一直到100就不增加了
```

## AlertDiglog
```kotlin
可以在当前界面里面弹出一个对话框，这个对话框时、是置顶于所有界面之上的，能屏蔽其他控件的交互能力
因此AlertDialog一般用于提示一些很重要的内容或者警告信息
比如防止用户删除重要内容
```
```kotlin
override fun onClick(v: View?) {
    when(v?.id){
        R.id.button->{
            AlertDialog.Builder(this).apply {//AlertDialog.Builder创建一个对话框
                setTitle("这是个提示框")//设置对话框的标题
                setMessage("这里很重要")//对话框里的内容
                setCancelable(false)//能不能用back关闭对话框: 不能
                setPositiveButton("可以"){//在对话框里面设置一个确定按钮的点击事件
                    dialog,which->{}//这里两个参数是lambda表达式的参数列表部分,但还不知道有啥用
                }
                setNegativeButton("关闭"){//在对话框里面设置一个取消按钮的点击事件
                    dialog,which->{}
                }
                show()//把对话框显示出来
            }
        }
    }
}
```
