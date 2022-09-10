---
title: Fragment
author: Bob
top: true
toc: true
mathjax: true
categories: Android学习
tags:
  - 第一行代码
  - Fragment
abbrlink: 
summary: 《第一行代码》的Fragment学习
date: 2022-02-12 00:00:00
---

# Fragment
```kotlin
Fragment是一种可以嵌入到Activity中的UI片段,能让程序更加合理和充分地利用大屏幕的空间
Fragment非常相似,同样有布局和生命周期的概念
```
## Fragment的使用
### 尝试一个简单的使用
```xml
left_fragment.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/button"
        android:layout_gravity="center_horizontal"
        android:text="Button"/>

    <!--android:gravity：是对view控件本身来说的，是用来设置view本身的文本应该显示在view的什么位置,默认值是左侧
    android:layout_gravity：是相对于包含改元素的父元素来说的，设置该元素在父元素的什么位置-->

</LinearLayout>
```
```xml
right_fragment.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#00ff00">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/textView"
        android:layout_gravity="center_horizontal"
        android:textSize="24sp"
        android:text="This is a fragment"/>
</LinearLayout>
```
```xml
activity_main.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <fragment
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:id="@+id/leftFrag"
        android:name="com.bo.a5_learnfragment.LeftFragment"/>

    <fragment
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:id="@+id/rightFrag"
        android:name="com.bo.a5_learnfragment.RightFragment"/>
    <!--这通过<fragment>标签在布局中添加Fragment，android:name来显式的声明要添加的Fragment类名-->
</LinearLayout>
```
```kotlin
LeftFragment.kt

/*这里要从androidx.fragment.app.Fragment继承,因为它可以让Fragment的特性在所有Android版本中保持一致*/
class LeftFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.left_fragment,container,false)
    }
}
```
```kotlin
RightFragment.kt

/*这里要从androidx.fragment.app.Fragment继承,因为它可以让Fragment的特性在所有Android版本中保持一致*/
class LeftFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.right_fragment,container,false)
    }
}
```

### 动态添加Fragment
```kotlin
Fragment的真正强大之处在于可以动态的添加到Activity中

我们可以在上一节的代码后完善
```
```xml
添加一个需要动态添加的布局
another_right_fragment.xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#ffff00">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/another_right_fragment"
        android:layout_gravity="center_horizontal"
        android:textSize="24sp"
        android:text="This is a another right fragment"/>

</LinearLayout>
```
```kotlin
AnotherRightFragment.kt中
class AnotherRightFragment:Fragment() {···}
```
```xml
activity_main.xml修改成

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <fragment
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:id="@+id/leftFrag"
        android:name="com.bo.a5_learnfragment.LeftFragment"/>
    <!--把右侧的Fragment改成了FrameLayout帧布局,在这个布局里面所有的元素默认都会放在布局的左上角-->
    <FrameLayout
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:id="@+id/rightLayout"
        android:layout_weight="1"/>

</LinearLayout>
``` 

```kotlin
MainActivity修改成
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {//给左侧Fragment中的按钮注册一个点击事件
            replaceFragment(RightFragment())//然后通过replaceFragment()动态添加RightFragment
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager//获取fragmentManager
        val transaction=fragmentManager.beginTransaction()//开启一个事务
        transaction.replace(R.id.rightLayout,fragment)//向容器中添加或者替换Fragment,replace(容器的id,待添加的Fragmnet)
        transaction.commit()
    }
}
``` 

### Fragment中实现返回栈
```kotlin
我们现在可以发现点击back后就直接退出了程序,但如果我们想back回上一个Fragment，就需要实现返回栈了
```
```kotlin
class MainActivity : AppCompatActivity() {
    ···

    private fun replaceFragment(fragment: Fragment){
        ···
        transaction.addToBackStack(null)
        ···
    }
}
添加一句transaction.addToBackStack(null)就可以了,
transaction.addToBackStack(null)方法可以接收一个名字用于描述返回栈的状态，一般传入null就可以了
```

### Fragment和Activity之间的交互
虽然Fragment是嵌入到Activity中，但是事实上Fragment和Activity式各自存在一个独立的类中，他们之间没有那么明显的方式直接调用来进行交互
如果想要在Activity中调用Fragment或者Fragment中调用Activity
#### Activity中调用Fragment
- 为了方便他俩之间的交互，FragmentManager提供了一种类似于findViewById()的方法,专门用于从布局文件中获取Fragment的实例
  `val fragment=supportFragmentManager.findFragmentById(R.id.leftFlag) as LeftFragment`
- 类似于findViewById()方法,kotlin-android-extensions插件也对findFragmentById()方法进行了扩展,允许直接使用id来自动获取实例
  `val fragment=leftFrag as LeftFragment`
#### Fragment中调用Activity
- 每个Fragment中可以通过getActivity()方法来得到和当前Fragment相关联的Activity实例
  ```kotlin
    if(activity!=null){
        val mainActvity=activity as MainActivity
    }
  ```
  由于getActivity()可能会返回null，所以我们需要先判空
- Fragment需要使用Context对象的时候，也可以用getActivity()方阿飞，因为获取到的Activity本身就是一个Context对象
#### Fragment之间通信
1. 在一个Fragment中可以得到与他相关的Activity
2. 通过这个Activity去获取另一个Fragment的实例

## Fragment的使命周期
### Fragment的状态
1. 运行状态
   ```kotlin
    Fragment正在运行的时候就是运行状态
   ```
2. 暂停状态
   ```kotlin
   一个Activity进入暂停状态(由于另一个未占满屏幕的Activity被添加到了栈顶),
   与他相关联的Fragment就会进入暂停状态
   ```
3. 停止状态
   ```kotlin
    Activity停止的时候与他关联的Fragment就停止了,或者通过FragmentTransaction的remove(),replace()将把Fragment从Activity移除,
    但在事务提交之前并调用了addToBackStack()方法,这时Fragment也会进入停止状态
   ```
4. 销毁状态
   ```kotlin
    Fragment总是依附于Activity存在，因此Activity销毁的时候Fragment就销毁了
    或者通过FragmentTransaction的remove(),replace()将把Fragment从Activity移除,
    但在事务提交之前并没有调用addToBackStack()方法,这时Fragment也会进入销毁状态
   ```
### 回调
```kotlin
onAttach(): 当Fragment和Activity建立关联的时候调用
onCreateView():为Fragment创建视图(加载布局)时调用
onActivityCreated():确保Tragment相关联的Activity已经创建完毕的时候调用
onDestroyView():当于Fragment关联的视图被移除的时候调用
onDetach():当Fragment和Activity解除关联时使用
```
[![bcxrX4.png](https://s1.ax1x.com/2022/03/08/bcxrX4.png)](https://imgtu.com/i/bcxrX4)

### 体验Fragment的生命周期
```kotlin
调整RightFragment来尝试一下

/*这里要从androidx.fragment.app.Fragment继承,因为它可以让Fragment的特性在所有Android版本中保持一致*/
class RightFragment:Fragment() {

    companion object{
        const val TAG="RightFragment"//kotlin中定义常量都是这种方法
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"onCreateView")
        return inflater.inflate(R.layout.right_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG,"onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG,"onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG,"onDetach")
    }
}
```

```kotlin
初始化后输出
RightFragment: onAttach
RightFragment: onCreate
RightFragment: onCreateView
RightFragment: onActivityCreated
RightFragment: onStart
RightFragment: onResume
```

```kotlin
点击button后产生一个右侧Fragment，输出
由于AnotherRightFragment替换了RightFragment,RightFragment停止
RightFragment: onPause
RightFragment: onStop
RightFragment: onDestroyView
如果替换的时候没有调用addToBackStack()，此时RightFragment就会进入销毁状态
onDestroy()和onDetach()会执行
```

```kotlin
点击back，RightFragment就会回到屏幕

RightFragment: onCreateView
RightFragment: onActivityCreated
RightFragment: onStart
RightFragment: onResume

可以看到onCreate()没有执行，因为我们借助addToBackStack()所以Activity并没有销毁
```

```kotlin
再按一次back

RightFragment: onPause
RightFragment: onStop
RightFragment: onDestroyView
RightFragment: onDestroy
RightFragment: onDetach
```
**值得一提的是**
Fragment可以通过onSaveInstanceState()方法来保存数据,因为进入停止状态的Fragment有可能会在内存不足的情况下被回收，可以通过onCreate(),onCreateView(),onActivityCreated()这三个方法中得到数据

## 动态加载布局的技巧
### 使用限定符
```kotlin
我们会发现平板应用采用的是双页模式(一面列表,一面内容),而手机只能使用一面(因为手机屏幕太小了)，那么我们怎么判断该使用单页还是双叶呢?
我们可以使用限定符(qualifier)
```
```xml

实践出真知 ,修改activity_main.xml

我们会有两个activity_main.xml,

layout/activity.xml对应单页模式
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent">


    <fragment
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/leftFrag"
        android:name="com.bo.a5_learnfragment.LeftFragment"/>

</LinearLayout>

layout-large/activity.xml对应双页模式
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <fragment
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:id="@+id/leftFrag"
        android:name="com.bo.a5_learnfragment.LeftFragment"/>

    <fragment
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="3"
        android:id="@+id/rightFrag"
        android:name="com.bo.a5_learnfragment.RightFragment"/>
</LinearLayout>
```
```kotlin
large是一个限定符,当屏幕被认为是large的设备就会自动加载layout-large文件夹下的布局,
而小屏幕的设备则还是会加载layout文件夹下的布局
```
### 限定符
[![bgepWQ.jpg](https://s1.ax1x.com/2022/03/08/bgepWQ.jpg)](https://imgtu.com/i/bgepWQ)

### 使用最小宽度限定符
```kotlin
large限定符成功解决了单双页的问题，但是large到底是多大呢?
有时候我们希望更加灵活的为不同设备加载布局
不管他是不是large这个时候就可以使用最小宽度限定符了

最小宽度限定符允许我们对屏幕的宽度指定一个最小值(dp为单位),然后以这个最小值为临界点，
屏幕大于这个值就加载一个布局，小于就加载另一个布局
```
**尝试尝试**
```xml
我们创建一个large-sw600dp/activity_main.xml
这个sw600dp意思就是大于600dp就会加载这个布局文件，小于就会加载默认的layout/activity_main.xml文件
```

## Fragment实践-简易的新闻应用
见项目