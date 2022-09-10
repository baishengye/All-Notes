---
title: Material Design
author: Bob
top: true
toc: true
mathjax: true
categories: Android学习
tags:
  - 第一行代码
  - Material Design
abbrlink: 
summary: 《第一行代码》的Material Design学习
date: 2022-02-12 00:00:00
---



# 最佳的UI体验,Material Design实战

## 什么是Material Design
精美的UI设计语言

## 学习Material Design中的控件

### 1. Toolbar
还记得我们每次打开的程序的标题栏吗？啊对，那不是`toolbar`做的.但是,那是`actionbar`做的,`Toolbar`可以说是`Actionbar`的升级版了。`Toolbar`不仅有`Actionbar`的所有功能,而且还更加灵活.

**Actionbar是默认显示的**
其实这是在项目中指定的主题来显示的
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.bo.a1_material">
    <application
        ···
        android:theme="@style/AppTheme">
        <!--这里android:theme指定了一个Theme.1_Material的主题-->
        ···
    </application>
</manifest>
```
那么这个Theme.1_Material主题到底在哪里呢?在res/values/themes.xml文件中
```xml
<resources>
    <style name="AppTheme" parent="Theme.AppCompat.DayNight.DarkActionBar">
        <item name="colorPrimary">@color/purple_500</item>
        <item name="colorPrimaryDark">@color/black</item>
        <item name="colorAccent">@color/black</item>
        <item name="android:textColorPrimary">@color/black</item>
        <item name="android:navigationBarColor">@color/purple_200</item>
        <item name="android:windowBackground">@color/white</item>
    </style>
</resources>
```
- [![qPPL0e.png](https://s1.ax1x.com/2022/03/17/qPPL0e.png)](https://imgtu.com/i/qPPL0e)
指定了一个叫做AppTheme的主题，他的父主题是Theme.Theme.AppCompat.DayNight.DarkActionBar是一个深色主题的意思
现在我们需要指定不带Actionbar的主题
通常有两种主题
- `Theme.AppCompat.NoActionBar`   深色主题
- `Theme.AppCompat.Light.NoActionBar`  浅色主题

**现在已经把ActionBar隐藏起来了，可以用ToolBar了**
```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    <!--xmlns:android="http://schemas.android.com/apk/res/android"指定了一个android的命名空间
    指定了新的命名空间之后，我们才可以用android:id,android:layout_height这种用法

    xmlns:app="http://schemas.android.com/apk/res-auto"指定了一个app的命名空间
    我们就可以用app:popupTheme这样的用法了

    由于有很多新的Materials属性在新系统中新增的,所以就需要兼容老系统,所以我们就不能用android:attribute，而要用app:attribute
    比如app:id或者app:layout_height等-->
    <androidx.appcompat.widget.Toolbar
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:id="@+id/toolbar"
        android:background="@color/purple_500"
        android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
        app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
    <!--androidx.appcompat.widget.Toolbar定义了一个Toolbar控件，这个控件widget.Toolbar是在appcompat库里面的
    ?attr/actionBarSize是指ActionBar的高度
    android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"指定Toolbar的颜色是深色的
    app:popupTheme="@style/ThemeOverlay.AppCompat.Light"指定弹出菜单是浅色的-->
</FrameLayout>
```
**ToolBar常见功能**
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    ···>
    <application
        ···>
        <activity
            ···
            android:label="Fruits">
            <!--android:label="Fruits"指定标签-->
            ···
        </activity>
    </application>
</manifest>
```

**把按钮放在toolbar中，空间不足就放在菜单里面**
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item
        android:id="@+id/backup"
        android:icon="@drawable/ic_backup"
        android:title="Backup"
        app:showAsAction="always"/>

    <item
        android:id="@+id/delete"
        android:icon="@drawable/ic_delete"
        android:title="Delete"
        app:showAsAction="ifRoom"/>

    <item
        android:id="@+id/setting"
        android:icon="@drawable/ic_settings"
        android:title="Setting"
        app:showAsAction="never"/>

    <!--item指定一个action按钮
    id指定一个唯一id
    icon指定一个图标
    title指定一个标题
    showAsAction指定按钮显示的位置
        never- 永远显示在菜单里
        ifRoom- 如果屏幕空间足够就显示在toolbar中,不够就在菜单中
        always- 永远显示在toolbar中
    但是Toolbar中的Action按钮只会显示图标，菜单中的Action按钮只会显示文字-->

</menu>
```
```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.backup->Toast.makeText(this,"点击了backup",Toast.LENGTH_SHORT).show()
            R.id.delete->Toast.makeText(this,"点击了delete",Toast.LENGTH_SHORT).show()
            R.id.setting->Toast.makeText(this,"点击了setting",Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
```

## 滑动菜单

### DrawerLayout
布局----在这个布局中允许放入两个直接子控件，
- 屏幕中显示的内容
- 滑动菜单中显示的内容
```xml
<androidx.drawerlayout.widget.DrawerLayout
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:id="@+id/drawLayout">

    <!--主屏幕中显示的内容-->
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.appcompat.widget.Toolbar
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:id="@+id/toolbar"
            android:background="@color/purple_500"
            android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
            app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
    </FrameLayout>

    <!--滑动菜单中的内容-->
    <!--android:layout_gravity="start"指定了滑动菜单在屏幕的哪边
    start是指系统语言是从左到右读就在右边，反之就在左边-->
    <!--android:layout_gravity="start"必须要指定，指定了的就是滑动菜单-->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#FFF"
        android:layout_gravity="start"
        android:text="菜单"
        android:textSize="30sp"/>
</androidx.drawerlayout.widget.DrawerLayout>
```
```kotlin
加入一个导航图标指示用户使用滑动菜单

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        ···
        supportActionBar?.let {//获得ActionBar的实例
            it.setDisplayHomeAsUpEnabled(true)//当ActionBar不为空的时候就让导航按钮显示出来
            it.setHomeAsUpIndicator(R.drawable.ic_menu)//设置一个导航按钮图标(默认是叫做home按钮,默认的图标是返回箭头，含义是返回上一个Activity)
        }
    }
    ··· 
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            /*home按钮的id永远是android.R.id.home*/
            android.R.id.home->binding.drawLayout.openDrawer(GravityCompat.START)
            ···
        }
        return true
    }
}
```

### NavigationView
滑动窗口里面用TextView太丑了，尝试尝试NavigationView

```xml
需要把库引入项目才行

dependencies {
    ···
    implementation 'com.google.android.material:material:1.0.0'
    Material库
    implementation 'de.hdoenhof:circleimageview:3.0.1'
    开源项目CircleImageView,可以轻松实现图片圆形化
}
```
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <group android:checkableBehavior="single">
        <!--指定这个组里面选择的方式:single是单选,all是多选,none是不能选-->
        <item
            android:id="@+id/navCall"
            android:icon="@drawable/nav_call"
            android:title="打电话"/>

        <item
            android:id="@+id/navFriend"
            android:icon="@drawable/nav_friends"
            android:title="朋友"/>

        <item
            android:id="@+id/navLocation"
            android:icon="@drawable/nav_location"
            android:title="位置"/>

        <item
            android:id="@+id/navTask"
            android:icon="@drawable/nav_task"
            android:title="记事本"/>
    </group>
</menu>
```
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="180dp"
    android:padding="10dp"
    android:background="@color/purple_200">

    <de.hdodenhof.circleimageview.CircleImageView
        android:layout_width="70dp"
        android:layout_height="70dp"
        android:id="@+id/iconImage"
        android:src="@drawable/nav_icon"
        android:layout_centerInParent="true"/>
    <!--android:src="@drawable/nav_icon"指定一张图片-->

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/mailText"
        android:layout_alignParentBottom="true"
        android:text="123456789@qq.com"
        android:textColor="#FFF"
        android:textSize="14sp"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/userText"
        android:layout_above="@+id/mailText"
        android:text="123456"
        android:textColor="#FFF"
        android:textSize="14sp"/>

</RelativeLayout>
```
```xml
<androidx.drawerlayout.widget.DrawerLayout
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:id="@+id/drawLayout">

    <!--主屏幕中显示的内容-->
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.appcompat.widget.Toolbar
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:id="@+id/toolbar"
            android:background="@color/purple_500"
            android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
            app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>
    </FrameLayout>

    <com.google.android.material.navigation.NavigationView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/navView"
        android:layout_gravity="start"
        app:headerLayout="@layout/nav_header"
        app:menu="@menu/nav_menu"
        />
    <!--app:headerLayout接收一个头部布局
    app:menu接收一个菜单布局
    app:headerLayout一定会在app:menu上面-->

</androidx.drawerlayout.widget.DrawerLayout>
```

## 悬浮按钮和可交互提示

### FloatingActionButton
是Material中的控件，可以轻松实现悬浮按钮
悬浮按钮会默认使用colorAccent作为按钮的颜色，还可以用一个图标来表明这个按钮的作用
```xml
<androidx.drawerlayout.widget.DrawerLayout
    ···>
    <!--主屏幕中显示的内容-->
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        ···
        <com.google.android.material.floatingactionbutton.FloatingActionButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/fab"
            android:layout_gravity="bottom|end"
            android:layout_margin="16dp"
            android:src="@drawable/ic_done"
            app:elevation="8dp"/>
        <!--android:layout_gravity="bottom|end"表明这个按钮在下边，并且end指定如果系统语言从左到右就按钮在右边，反之在左边
        app:elevation="8dp指定悬浮高度，高度值越大，投影范围越大(悬浮按钮有影子),影子越淡-->
    </FrameLayout>
    ···
</androidx.drawerlayout.widget.DrawerLayout>
```

### Snackbar
Toast提示是不是有点拉，可以用Snackbar提示
但是Toast并非是不如Snackbar，他们有不同的应用场景
- Toast只能告诉用户现在发生了什么事情，用户只能被动接收这件事情
- Snackbar允许在提示中加入一个可交互按钮，当用户点击按钮的时候，就可以执行一些额外的逻辑操作

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        ···
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "删除数据", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                    Toast.makeText(this, "数据恢复", Toast.LENGTH_SHORT).show()
                }.show()
        }

    }
    ···
```
但是此时我们会发现Snackbar弹出的时候会把悬浮按钮遮住，但是这个问题我们可以用CoordinatorLayout解决

### CoordinatorLayout
- CoordinatorLayout可以是一种加强版的FrameLayout(帧布局)
- CoordinatorLayout会监听所有子控件的各种事件，并且自动帮助我们做出最为合理的响应

```xml
<androidx.drawerlayout.widget.DrawerLayout
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:id="@+id/drawLayout">

    <!--主屏幕中显示的内容-->
    <androidx.coordinatorlayout.widget.CoordinatorLayout
        ···>
        ···
    </androidx.coordinatorlayout.widget.CoordinatorLayout>

    ···
</androidx.drawerlayout.widget.DrawerLayout>
```
其实就是把原本的FrameLayout布局改成了androidx.coordinatorlayout.widget.CoordinatorLayout就行了
效果就是当点击悬浮按钮Snackbar出现的时候，悬浮按钮会上移来适应Snackbar是自己不会被Snackbar遮住
**但是Snackbar并不是CoordinatorLayout的子控件，而是DrawerLayout的子控件,为什么也能成功呢?**
是因为Snackbar.make()传入了一个view,这是用来指定Snackbar是哪个View触发的，所以传入的是悬浮按钮控件本身，悬浮按钮控件是CoordinatorLayout的子控件，所以能成功


## 卡片式布局
虽然之前的操作已经有了很多的内容，但是屏幕中心追重要的部分还没有内容

### MaterialCardView
MaterialCardView其实也是一个升级版的FrameLayout,他只是额外提供了圆角和阴影等效果，看起来会更加立体
**直接来，在代码里看作用吧**
```xml
dependencies {
    ···
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    /*添加recyclerview的依赖*/
    implementation 'com.github.bumptech.glide:glide:4.9.0'
   /* 添加gilde的依赖,gilde可以加载图片，甚至是gif图和本地视频*/
}
```
```xml
<androidx.drawerlayout.widget.DrawerLayout
    ···>

    <!--主屏幕中显示的内容-->
    <androidx.coordinatorlayout.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        ···
        <androidx.recyclerview.widget.RecyclerView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/recyclerView"/>
        ···
    </androidx.coordinatorlayout.widget.CoordinatorLayout>
    ···
</androidx.drawerlayout.widget.DrawerLayout>
```
```xml
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="5dp"
    app:cardCornerRadius="4dp">

    <!--由于MaterialCardView可以理解成一种高级的FrameLayout，所以他是不方便定位的，所以我们在里面嵌套了一个LinearLayout方便定位-->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <ImageView
            android:id="@+id/fruitImage"
            android:layout_width="match_parent"
            android:layout_height="100dp"
            android:scaleType="centerCrop"
            android:contentDescription="TODO" />
        <!--android:scaleType="centerCrop"由于原本的图片可能大小不一，那么我们就需要把图片缩放到一样，
        centerCrop是超出部分会被裁掉-->

        <TextView
            android:id="@+id/fruitName"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:layout_margin="5dp"
            android:textSize="16sp" />
    </LinearLayout>

</com.google.android.material.card.MaterialCardView>
```
```kotlin
class FruitAdapter(private val context: Context, private val fruitList:List<Fruit>):
RecyclerView.Adapter<FruitAdapter.ViewHolder>(){
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val fruitName:TextView=view.findViewById(R.id.fruitName)
        val fruitImage:ImageView=view.findViewById(R.id.fruitImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.fruit_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit=fruitList[position]
        holder.fruitName.text=fruit.name
        Glide.with(context).load(fruit.imageId).into(holder.fruitImage)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }
}
```
```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fruits=mutableListOf(
        Fruit("Apple", R.drawable.apple),
        Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange),
        Fruit("Watermelon", R.drawable.watermelon),
        Fruit("Pear", R.drawable.pear),
        Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pineapple),
        Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry),
        Fruit("Mango", R.drawable.mango))

    private val fruitList=ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        ···
        initFruits()
        /*GridLayoutManager标准网格布局的布局管理器,此处是指在这个布局中，一行两个，对齐高度一致*/
        val layoutManager= GridLayoutManager(this,2)
        binding.recyclerView.layoutManager=layoutManager
        val adapter=FruitAdapter(this,fruitList)
        binding.recyclerView.adapter=adapter
    }

    private fun initFruits(){
        fruitList.clear()
        repeat(50){
            val index=(0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }
    ···
}
```
我们会发现Toolbar会被Recyclerview挡住，这就要借助AppBarlayout来解决了

### AppBarLayout
CoordinatorLayout是加强版的FrameLayout，在不进行明确定位的情况下所有控件会默认放在布局的左上角，从而产生了遮挡的情况
AppBarLayout实际上是个垂直方向上的Linearlayout,他的内部做了很多滚动事件的封装，并且应用了一些Matrial Design的设计理念
我们只需要把Toolbar放在AppBarLayout中就可以了
```xml
<androidx.drawerlayout.widget.DrawerLayout
    ···>
    <!--主屏幕中显示的内容-->
    <androidx.coordinatorlayout.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.google.android.material.appbar.AppBarLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <androidx.appcompat.widget.Toolbar
                ···
                app:layout_scrollFlags="scroll|enterAlways|snap"/>
            <!--app:layout_scrollFlags当有滚动事件的时候toolbar会发生什么?
            scroll:RecyclerView向上滚动的时候Toolbar会跟着向上滚动并且隐藏
            enterAlways:RecyclerView向下滚动的时候，Toolbar会跟着向下滚动，并显示
            snap:Toolbar还没有完全隐藏或显示的时候，就会根据当前滚动的距离，自动选择隐藏还是显示-->

        </com.google.android.material.appbar.AppBarLayout>


        <androidx.recyclerview.widget.RecyclerView
            ···
            app:layout_behavior="@string/appbar_scrolling_view_behavior"/>
        <!--app:layout_behavior="@string/appbar_scrolling_view_behavior"
        是个布局指定行为，当RecyclerView有滚动行为的时候会把滚动事件传给AppBarLayout进行一个处理-->
    ···
    </androidx.coordinatorlayout.widget.CoordinatorLayout>
    ···
</androidx.drawerlayout.widget.DrawerLayout>
```

## 下来刷新
**SwipeRefreshLaout就是用于下拉刷新功能的核心类**在Androidx库中
把想要实现下拉刷新的控件放到SwipeRefreshLaout中，就可迅速让这个控件刷新
```xml
dependencies {
    ···
    implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
}
```
```xml
<androidx.drawerlayout.widget.DrawerLayout
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:id="@+id/drawLayout">

    <!--主屏幕中显示的内容-->
    <androidx.coordinatorlayout.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        ···
        <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
            android:id="@+id/swipeRefresh"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior">
            <androidx.recyclerview.widget.RecyclerView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:id="@+id/recyclerView"/>
            <!--app:layout_behavior="@string/appbar_scrolling_view_behavior"
            是个布局指定行为，当RecyclerView有滚动行为的时候会把滚动事件传给AppBarLayout进行一个处理-->

        </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
        ···
    </androidx.coordinatorlayout.widget.CoordinatorLayout>

</androidx.drawerlayout.widget.DrawerLayout>
```
```kotlin
class MainActivity : AppCompatActivity() {
    ···
    override fun onCreate(savedInstanceState: Bundle?) {
        ···
        binding.swipeRefresh.setColorSchemeResources(R.color.purple_700)
        //设置刷新时进度条的颜色
        binding.swipeRefresh.setOnRefreshListener {
            //下拉刷新的监听器
            refreshFruits(adapter)
        }
    }

    private fun refreshFruits(adapter: FruitAdapter){
        //在子线程中开启对UI的操作
        thread {
            Thread.sleep(1000)//让子线程沉睡1秒，不然看不到刷新的效果
            runOnUiThread { //要借助异步消息处理机制，让UI操作在主线程中进行
                initFruits()
                adapter.notifyDataSetChanged()//通过适配器来刷新数据变化
                binding.swipeRefresh.isRefreshing=false//表示刷新事件结束了，并且会隐藏刷新进度条,默认是true不结束不隐藏
            }
        }
    }
    ···
}
```

## 可折叠式标题栏
我们当前的标题的使用Toolbar，但是其实和Actionbar差别不大，只不过可以响应recyclerview的滚动事件而已,我们可以使用**collapsingToolbarLayout让标题栏更华丽**
- collapsingToolbarLayout是不能单独存在的，他必须是AppBarLayout的子布局
- AppBarLayout也是不能单独存在的，它必须是CoordinatorLayout的子布局
```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.google.android.material.appbar.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="250dp"
        android:id="@+id/appBar">

        <com.google.android.material.appbar.CollapsingToolbarLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/collapsingToolbar"
            android:background="@color/purple_200"
            android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
            app:contentScrim="@color/black"
            app:layout_scrollFlags="scroll|exitUntilCollapsed">
        <!--app:contentScrim指定CollapsingToolbarLayout趋于折叠状态以及折叠之后的背景色
        exitUntilCollapsed表示滚动完成后折叠在标题栏，成为一个普通的Toolbar
        scroll表示随着内容滚动-->
        <ImageView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/fruitImageView"
            android:scaleType="centerCrop"
            app:layout_collapseMode="parallax"/>

         <androidx.appcompat.widget.Toolbar
             android:layout_width="match_parent"
             android:layout_height="?android:attr/actionBarSize"
             android:id="@+id/toolbar"
             app:layout_collapseMode="pin"/>
            <!--app:layout_collapseMode表示折叠过程中的折叠模式
            pin是指折叠过程中位置始终不变
            parallax是指折叠过程中有一定的偏移-->

        </com.google.android.material.appbar.CollapsingToolbarLayout>
    </com.google.android.material.appbar.AppBarLayout>

    <androidx.core.widget.NestedScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">
        <!--NestedScrollView和ScrollView相似,前者除了允许以滚动的方式查看屏幕外的数据,而且还增加了嵌套响应滚动事件的功能
        但是他们都只允许其内存在一个直接子布局，所以需要一个LinearLayout把多个布局封装起来-->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <com.google.android.material.card.MaterialCardView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="35dp"
                android:layout_marginLeft="15dp"
                android:layout_marginBottom="15dp"
                android:layout_marginRight="15dp"
                app:cardCornerRadius="4dp">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:id="@+id/fruitContentText"
                    android:layout_margin="10dp"/>
            </com.google.android.material.card.MaterialCardView>
        </LinearLayout>
    </androidx.core.widget.NestedScrollView>

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:src="@drawable/ic_comment"
        app:layout_anchor="@id/appBar"
        app:layout_anchorGravity="bottom|end"
        tools:ignore="SpeakableTextPresentCheck"
        android:id="@+id/floatingBtn"/>
    <!--app:layout_anchor设置一个锚点在标题栏里面,
    app:layout_anchorGravity指定锚点在appbar的哪个地方-->
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```
```kotlin
class FruitActivity:AppCompatActivity() {
    companion object{
        const val FRUIT_NAME="fruit_name"
        const val FRUIT_IMAGE_ID="fruit_image_id"
    }
    private lateinit var binding: ActivityFruitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fruitName=intent.getStringExtra(FRUIT_NAME)?:""
        val fruitImageId=intent.getIntExtra(FRUIT_IMAGE_ID,0)
        setSupportActionBar(binding.toolbar)//设置标题栏是toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//显示导航按钮
        binding.collapsingToolbar.title=fruitName//标题栏的标题
        Glide.with(this).load(fruitImageId).into(binding.fruitImageView)
        //通过上下文加载图片到对应上下文(视图)的对应图片视图里面
        binding.fruitContentText.text=generateFruitContent(fruitName)//设置屏幕中心卡片中的文字内容

        binding.floatingBtn.setOnClickListener {
            Snackbar.make(it,"点击了悬浮按钮",Snackbar.LENGTH_SHORT)
                .setAction("我看到了") {
                    Toast.makeText(this, "看的好", Toast.LENGTH_SHORT).show()
                }.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {//标题栏(包括菜单中)中的按钮按下之后都会回调这个函数
        when(item.itemId){
            android.R.id.home->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun generateFruitContent(fruitName:String)=fruitName.repeat(500)
}
```
```kotlin
class FruitAdapter(private val context: Context, private val fruitList:List<Fruit>):
RecyclerView.Adapter<FruitAdapter.ViewHolder>(){
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val fruitName:TextView=view.findViewById(R.id.fruitName)
        val fruitImage:ImageView=view.findViewById(R.id.fruitImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.fruit_item,parent,false)
        val holder=ViewHolder(view)//此处的view是指activity_fruit.xml的整个布局,holder获取到整个布局的情况
        holder.itemView.setOnClickListener { //只要是哪个子控件被点击了，就会获取这个子控件的水果名字和水果图片id
            val position=holder.adapterPosition
            val fruit=fruitList[position]
            val intent=Intent(context,FruitActivity::class.java).apply { //再创建一个意图(打开FruitActivity)
                putExtra(FruitActivity.FRUIT_NAME,fruit.name)//并且把说过名字和水果图片id传过去
                putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.imageId)
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit=fruitList[position]
        holder.fruitName.text=fruit.name
        Glide.with(context).load(fruit.imageId).into(holder.fruitImage)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }
}
```

### 充分利用系统状态栏空间 
- android5.0时代之前，是无法对状态栏的背景或颜色进行设置的
- 之后就可以了

**android:fitsSystemWindows这个属性可以让背景与系统状态栏融合**
- 只需要在CoordinatorLayout,AppBarLayout,CollapsingToolbarLayout这种嵌套结构的布局中，将android:fitsSystemWindows指定为true，就表示这个控件会出现在系统状态栏中了
```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
    ···
    android:fitsSystemWindows="true">

    <com.google.android.material.appbar.AppBarLayout
        ···
        android:fitsSystemWindows="true">

        <com.google.android.material.appbar.CollapsingToolbarLayout
            ···
            android:fitsSystemWindows="true">
        <ImageView
            ···
            android:fitsSystemWindows="true"/>
         ···
        </com.google.android.material.appbar.CollapsingToolbarLayout>
    </com.google.android.material.appbar.AppBarLayout>
    ···
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```
看得出来,如果要把最下级的控件的效果展示到系统控制栏的话，需要把所有包含他的父布局都加上android:fitsSystemWindows="true"属性，而且还要把原来的系统状态栏颜色设置成透明
```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
    <style name="Theme.1_Material" parent="Theme.AppCompat.Light.NoActionBar">
        ···
    </style>
    <style name="FruitActivityTheme" parent="Theme.1_Material">
        <item name="android:statusBarColor">@android:color/transparent</item>
        <!--把FruitActivity所用的布局主题设置成透明的,并且Theme.1_Material是他的父主题，FruitActivityTheme拥有所有Theme.1_Material主题的特性-->
    </style>
</resources>
```