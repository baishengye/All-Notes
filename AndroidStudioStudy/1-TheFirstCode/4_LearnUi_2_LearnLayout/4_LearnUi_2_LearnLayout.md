# 详解三种基本布局
[![bBTkgU.md.png](https://s1.ax1x.com/2022/03/06/bBTkgU.md.png)](https://imgtu.com/i/bBTkgU)
## `LinearLayout`
```xml
线性布局,很常用,
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <!--android:orientation="vertical"   这个指定了线性排列的方向是vertical竖直的，如果写成horizontal就是水平的-->

    <!--值得注意的是
    如果LinearLayout的排列方式是horizontal,内部的空间就一定不能将宽度指定成match_parent,否则，单独的一个控件就会把整个水平方向占满
    如果LinearLayout的排列方式是vertical,内部的空间就一定不能将高度指定成match_parent,否则，单独的一个控件就会把整个竖直方向占满
    布局里如果没写android:orientation那么默认就是horizontal-->

    <Button
        android:id="@+id/button1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="top"
        android:text="Button 1"/>

    <!--android:layout_gravity可以指定这些控件的对齐方式
    值得注意的是:
    如果LinearLayout的排列方式是horizontal,只有垂直方向上的对齐方式才会生效
    如果LinearLayout的排列方式是vertical,只有水平方向上的对齐方式才会生效-->
    <Button
        android:id="@+id/button2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:text="Button 2"/>

    <Button
        android:id="@+id/button3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom"
        android:text="Button 3"/>

</LinearLayout>
```
```xml
LinearLayout的重要属性 :  Android:layout_weight
这个属性允许我们使用比例的方式来指定控件的大小，他在手机屏幕的适配性方面可以起到重要的作用
```
```xml
比如我们编写一个消息发送界面，需要一个文本编辑框和一个发送按钮

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:id="@+id/input_message"
        android:layout_weight="4"
        android:hint="输入"/>

    <Button
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:id="@+id/send"
        android:layout_weight="1"
        android:text="提交"/>

    <!--我们将layout_width设置成了0dp，这样还能显示出来吗?
    答案是可以的，因为我们设置了layout_weight="1",
    Button和EditText都设置成了1,那么Button和EditText平分这一行的宽度
    这是什么原理呢?
    系统会把LinearLayout下所有控件指定的layout_weight值相加得到一个总值sum，
    然后用每个控件的layout_weight/sum得到的比例就是控件的占有宽度
    -->
</LinearLayout>
```
```xml
更美观的方式

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:id="@+id/input_message"
        android:layout_weight="1"
        android:hint="输入"/>

    <Button
        android:layout_height="wrap_content"
        android:id="@+id/send"
        android:layout_width="wrap_content"
        android:text="提交"/>

    <!--可以看到我们只是指定了EditText的layout_weight，而Button没指定layout_weight，那么Button还会显示吗?
    答案是会显示.
    因为我们为Button指定了layout_width是和文本一样宽，那么系统就会先分配给Button需要的宽度
    然后把剩下的宽度都给其他的控件按照layout_weight去分配-->

</LinearLayout>
```

## `RelativeLayout`
```xml
根据父布局排列
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <Button
        android:id="@+id/button1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_alignParentTop="true"
        android:text="Button 1"/>

    <Button
        android:id="@+id/button2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_alignParentTop="true"
        android:text="Button 2"/>

    <Button
        android:id="@+id/button3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:text="Button 3"/>

    <Button
        android:id="@+id/button4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_alignParentBottom="true"
        android:text="Button 4"/>

    <Button
        android:id="@+id/button5"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_alignParentBottom="true"
        android:text="Button 5"/>
    
    <!--Button1: 和父布局的左上角对齐
    Button2: 和父布局的右上角对齐
    Button3: 和父布局的中心对齐
    Button4: 和父布局的左下角对齐
    Button5: 和父布局的右下对齐-->
</RelativeLayout>
```
```xml
根据其他控件来定位

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:id="@+id/button3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:text="Button 3"/>

    <Button
        android:id="@+id/button1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@id/button3"
        android:layout_toLeftOf="@id/button3"
        android:text="Button 1"/>

    <Button
        android:id="@+id/button2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@id/button3"
        android:layout_toRightOf="@id/button3"
        android:text="Button 3"/>

    <Button
        android:id="@+id/button4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/button3"
        android:layout_toLeftOf="@id/button3"
        android:text="Button 4"/>

    <Button
        android:id="@+id/button5"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/button3"
        android:layout_toRightOf="@id/button3"
        android:text="Button 5"/>

    <!--其实根据控件来定位其实重要的就是一类代码
    android:layout_below="@id/button3"    在  button3的下面
    android:layout_above="@id/button3"     在  button3的下面
    android:layout_toLeftOf="@id/button3"  在 button3的左边
    android:layout_toRightOf="@id/button3"  在 button3的右边
    值得注意的是button3是被引用的控件，所以button3要在其他控件之前注册-->


    <!--RelativeLayout 中还有另外一组相对于控件进行定位的属性:
    android:layout_alignLeft  表示让一个控件的左边缘和另一个控件的左边缘对齐
    android:layout_alignRight 表示让一个控件的右边缘和另一个控件的右边缘对齐 
    android:layout_alignTop  表示让一个控件的上边缘和另一个控件的上边缘对齐
    android:layout_alignBottom  表示让一个控件的下边缘和另一个控件的下边缘对齐-->
</RelativeLayout>
```
## `FrameLayout`
```xml
帧布局:应用场景较少
所有控件默认都会放在布局的左上角
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/textView"
        android:text="TextViet"/>

    <Button
        android:id="@+id/button1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button 1"/>

    <!--我们会发现现在Button是在TextView的上面的，因为默认所有控件都会在左上角
    而Button又是后注册的，所以他会在上面-->
</FrameLayout>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/textView"
        android:layout_gravity="left"
        android:text="TextView"/>

    <Button
        android:id="@+id/button1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="right"
        android:text="Button 1"/>

    <!--FrameLayout中也可以使用layout_gravity来指定对齐方式,这个地方就是左对齐和右对齐-->
</FrameLayout>
```