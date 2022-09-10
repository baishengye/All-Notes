---
title: BroadcastReveiver
author: Bob
top: true
toc: true
mathjax: true
categories: Android学习
tags:
  - 第一行代码
  - BroadcastReveiver
abbrlink: 
summary: 《第一行代码》的BroadcastReveiver学习
date: 2022-02-12 00:00:00
---


# 广播机制(Broadcast)
```kotlin
Android中的广播机制更加灵活:
因为Android中的每个应用都可以堆自己感兴趣的广播进行注册
这样该程序就只会收到自己关心的广播内容
这些广播可能是来自系统的，也可能是来自其他应用的
发送广播是通过intent
接受广播是通过BroadcastReveiver
```
**广播分成两类:**
- 标准广播:完全异步执行,在广播发出之后，几乎所有broadcastReceiver会在同一时间收到这条广播消息，因此他们之间没有任何先后顺序可言,这种广播的效率比较高，但同时他也是无法被截断的
[![bRQPOg.png](https://s1.ax1x.com/2022/03/09/bRQPOg.png)](https://imgtu.com/i/bRQPOg)
- 有序广播:同步执行,广播发出之后，同样一时间只会有一个BroadcastReceiver能够接收到这条广播消息，当BroadcastReceiver中的逻辑执行完了之后，广播才会继续传递，此时的BroadcastReceiver是有先后顺序的，优先级高的会先收到广播消息，并且前面的BroadcastReceiver可以截断消息，这样后面的BroadcastReceiver就无法接收到广播消息了
[![bRQFmQ.png](https://s1.ax1x.com/2022/03/09/bRQFmQ.png)](https://imgtu.com/i/bRQFmQ)


## 接收系统广播
我们可以根据自己感兴趣的广播，自由的注册BroadcastReceiver这样相应的广播发出时候，就会有相应的BroadcastReceiver能够接收广播，并且可以在内部进行逻辑处理
**注册BroadcastReceiver的方式**
- 在代码中动态注册
- 在AdnroidManifest.xml中静态注册
  
### 动态广播
```kotlin
实践出真知

class MainActivity : AppCompatActivity() {
    lateinit var timeChangeReceiver:TimeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


/*intent和intentFilter的区别
        后者比前者多了个筛选作用
        筛选条件:
        action、data和category*/

        val intentFilter=IntentFilter()//定义一个有过滤器的意图
        intentFilter.addAction("android.intent.action.TIME_TICK")
        //给意图添加一个筛选条件(筛选出Action是android.intent.action.TIME_TICK的意图)
        timeChangeReceiver=TimeChangeReceiver()//实例化一个时间改变广播接收器
        registerReceiver(timeChangeReceiver,intentFilter)//注册一个广播接收器timeChangeReveiver来监听intentFilter这个意图
    }

    override fun onDestroy() {
        super.onDestroy()
        //当这个活动销毁的时候就把接收器取消注册(一定要取消)
        unregisterReceiver(timeChangeReceiver)
    }

    inner class TimeChangeReceiver:BroadcastReceiver(){
        //定义一个接收器继承自BroadcastReceiver,
        override fun onReceive(context: Context, intent: Intent) {
            //当接收到一个消息就Toast一段话
            Toast.makeText(context,"Time Changed",Toast.LENGTH_LONG).show()
        }
    }
}
```
注册了注册一个广播接收器timeChangeReveiver来监听有这个动作(android.intent.action.TIME_TICK)的intentFilter这个意图,一旦时间过了一分钟广播接收器就会收到时间过了一分钟这个广播，然后Toast一句话

### 静态广播
- 动态注册的广播接收器虽然可以自由的控制注册和注销，灵活性有优势，但是他只有在程序启动后才能接收广播.
- 那么我们就可以用静态注册来在程序未启动的时候监听系统广播,但是这种方式给了恶意软件很大的操作空间，所以Android系统几乎每个版本都在削减静态注册的功能
- 在Android8.0之后所有的隐式广播都不允许用讲堂注册的方式来接收了，**隐式广播指的是那些没有具体指定发送给哪个应用程序的广播，大多数系统广播属于隐式广播，但是还有小部分的允许静态注册的方式接收**[小部分见官网](https://developer.android.google.cn/guide/components/broadcast-exceptions.html)
```kotlin
实践出真知

新建一个Other->BroadcastReceiver

class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Toast.makeText(context,"Boot Complete",Toast.LENGTH_LONG).show()
        /*但是注意在onReceive中不要使用太复杂的逻辑因为BroadcastReceiver中不允许开启线程
        当onReceive运行太久而没结束程序就会出现错误*/
    }

}
```
```xml
静态注册还需要去AndroidManifest.xml中注册这个广播接收器并且声明他所需要的权限
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.bo.a6_learnbroadcastreceiver">
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <!--这里是声明这个Activity有android.permission.RECEIVE_BOOT_COMPLETED权限-->

    <application
        ···>

        <!--android:name指定具体注册哪一个BroadcastReceiver
        android:enabled:是否启用这个广播接收器
        android:exported:是否允许接收本程序以外的广播-->
        <receiver
            android:name=".BootCompleteReceiver"
            android:enabled="true"
            android:exported="true">

            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <!--只有行为是android.intent.action.BOOT_COMPLETED的意图才会被这个广播接收器接收-->
            </intent-filter>

            <receiver
            android:name=".BootCompleteReceiver"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <!--监听启动的时候-->
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <!-- 只有行为是android.intent.action.BOOT_COMPLETED的意图才会被这个广播接收器接收 -->
            </intent-filter>
            <!--但是Android API Level8 以上的时候，程序可以安装在SD卡上。如果程序安装在SD卡上，
            那么在BOOT_COMPLETED广播发送之后，SD卡才会挂载，因此程序无法监听到该广播
            所以我么需要同时监听开机和sd卡挂载。（也不能只监听挂载就认为开机了，因为有的手机没有sd卡）-->
            <intent-filter >
                <action android:name="android.intent.action.MEDIA_MOUNTED"/>
                <action android:name="android.intent.action.MEDIA_UNMOUNTED"/>
                <data android:scheme="file">
                </data>
            </intent-filter>
        </receiver>
        </receiver>

        ···
    </application>

</manifest>
```
**遗憾的是我没有运行出效果，还不知道错误在哪里,希望以后能找出来**
**补**：我找到问题所在了哈哈哈哈:
- Android API Level8 以上的时候，程序可以安装在SD卡上。如果程序安装在SD卡上，
- 那么在BOOT_COMPLETED广播发送之后，SD卡才会挂载，因此程序无法监听到该广播
- 所以我么需要同时监听开机和sd卡挂载。（也不能只监听挂载就认为开机了，因为有的手机没有sd卡）

## 发送自定义广播
### 发送标准广播
**发送广播之前我们需要定义一个广播接收器来准备接收此广播**
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.bo.a6_learnbroadcastreceiver">
    ···
    <application
        ···>

        <receiver
            android:name=".MyBroadcastReceiver"
            android:enabled="true"
            android:exported="true">

            <intent-filter>
                <action android:name="com.bo.a6_learnbroadcastreceiver.MY_BROADCAST"/>
               <!-- 告诉接收器我们待会接收到"com.bo.a6_learnbroadcastreceiver.MY_BROADCAST"这样一条广播-->
            </intent-filter>
        </receiver>

        ···
    </application>

</manifest>
```
```kotlin
广播接收器接收后的执行逻辑
class MyBroadcastReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context,"在定义的广播接收器中接收到了广播",Toast.LENGTH_LONG).show()
    }
}
```
```xml
activity_main.xml中
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
   ···>

    //定义一个按钮作为发出广播的触发点
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/button"
        android:text="发送广播"/>

</LinearLayout>
```
```kotlin
按钮逻辑
class MainActivity : AppCompatActivity() {
    ···

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            var intent=Intent("com.bo.a6_learnbroadcastreceiver.MY_BROADCAST")
            /*发出一条"com.bo.a6_learnbroadcastreceiver.MY_BROADCAST"这样的广播*/
            intent.setPackage(packageName)
            /*intent.setPackage至关重要,由于Android8.0之后无法静态注册隐式广播，但是我们的广播接收器就是静态注册的,而且默认情况下就是隐式广播
            所以我们要想办法把我们的广播变成显式广播,我们通过setPackage()指定了广播发给packageName，然后我们的广播就变成了显式广播*/
            sendBroadcast(intent)
        }
        ···
}

```
**这个倒是成功了**

### 发送有序广播
与标准广播不同,有序广播是可以被截断的，而且是一种同步执行的广播
**那么我们在创建一个广播接收器**
```kotlin
我们可以再定义一个广播接收器

class AnotherBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Toast.makeText(context,"在另一个广播接收器中接收到了广播",Toast.LENGTH_LONG).show()
    }
}
```
```xml
并且注册

<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    ···>
    ···

    <application
        ···>
        <receiver
            android:name=".AnotherBroadcastReceiver"
            android:enabled="true"
            android:exported="true">

            <intent-filter>
                <action android:name="com.bo.a6_learnbroadcastreceiver.MY_BROADCAST"/>
            </intent-filter>
        </receiver>
        ···
    </application>

</manifest>
```
我们会发现点击按钮之后Toast出两句话,这是因为现在的广播还是标准广播，我们可以尝试改成有序广播
```kotlin
修改MainActivity.kt
class MainActivity : AppCompatActivity() {
    ···

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            var intent=Intent("com.bo.a6_learnbroadcastreceiver.MY_BROADCAST")
            intent.setPackage(packageName)
            /*intent.setPackage至关重要,由于Android8.0之后无法静态注册隐式广播，但是我们的广播接收器就是静态注册的,而且默认情况下就是隐式广播
            所以我们要想办法把我们的广播变成显式广播,我们通过setPackage()指定了广播发给packageName，然后我们的广播就变成了显式广播*/
            
            sendOrderedBroadcast(intent,null)//发送广播的方式
            /*这里有两个参数，第一个是inent(广播的意图),第二个是权限(null就行)*/
        }
        ···
    }
    ···
}
```
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.bo.a6_learnbroadcastreceiver">
    ···

    <application
        ···>
        <receiver
            android:name=".AnotherBroadcastReceiver"
            android:enabled="true"
            android:exported="true">

            <intent-filter>
                <action android:name="com.bo.a6_learnbroadcastreceiver.MY_BROADCAST"/>
            </intent-filter>
        </receiver>


        ···

        <receiver
            android:name=".MyBroadcastReceiver"
            android:enabled="true"
            android:exported="true">
            <intent-filter android:priority="100">
                <action android:name="com.bo.a6_learnbroadcastreceiver.MY_BROADCAST" />
                <!-- 告诉接收器我们待会接收到"com.bo.a6_learnbroadcastreceiver.MY_BROADCAST"这样一条广播 -->
            </intent-filter>
        </receiver>
        <!--<intent-filter android:priority="100"是设置这个接收器的优先级，优先级高的先收到 -->
        ···
    </application>

</manifest>
```
```kotlin
class MyBroadcastReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context,"在定义的广播接收器中接收到了广播",Toast.LENGTH_LONG).show()
        abortBroadcast()//表示将广播截断，后面的广播接收器就接收不到广播了
    }
}
```
**发现只有一个Toast出来了**

## Demo:利用广播实现强制下线
详情见项目