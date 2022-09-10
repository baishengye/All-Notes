---
title: Service
author: Bob
top: true
toc: true
mathjax: true
categories: Android学习
tags:
  - 第一行代码
  - Service
abbrlink: 
summary: 《第一行代码》的Service学习
date: 2022-02-12 00:00:00
---


# 后台工作者----Service

## 什么是Service
- `Service`是`Android`中实现程序后台运行的解决方案
- 适合执行不需要和用户交互而且还要长期运行的任务
- `Service`不需要依赖任何用户界面
- 即使`Service`被切换到了后台仍然能保持正常运行
- `Service`不是单独运行在一个进程里面，而是依赖于创建`Service`时所在的应用程序进程，当某个应用程序进程被杀掉，所有依赖于该进程的`Service`都会停止
- 事实上`Service`是不会自动开启线程的，所有代码都是默认运行再主线程当中，所以我们需要在`Service`的内部手动开启进程,并且在这里执行具体的任务，否则有可能出现主线程被阻塞的情况

## 补--进程与线程的区别**
### 线程的基本概念
　　线程是进程中执行运算的最小单位，是进程中的一个实体，是被系统独立调度和分派的基本单位，线程自己不拥有系统资源，只拥有一点在运行中必不可少的资源，但它可与同属一个进程的其它线程共享进程所拥有的全部资源。一个线程可以创建和撤消另一个线程，同一进程中的多个线程之间可以并发执行。
```kotlin
    好处 ：（1）易于调度。

               （2）提高并发性。通过线程可方便有效地实现并发性。进程可创建多个线程来执行同一程序的不同部分。

               （3）开销少。创建线程比创建进程要快，所需开销很少。。

               （4）利于充分发挥多处理器的功能。通过创建多线程进程，每个线程在一个处理器上运行，从而实现应用程序的并发性，使每个处理器都得到充分运行
```
### 进程与线程
- 进程：每个进程都有独立的代码和数据空间（进程上下文），进程间的切换会有较大的开销，一个进程包含1--n个线程。（进程是资源分配的最小单位）
- 线程：同一类线程共享代码和数据空间，每个线程有独立的运行栈和程序计数器(PC)，线程切换开销小。（线程是cpu调度的最小单位）


线程和进程一样分为五个阶段：创建、就绪、运行、阻塞、终止。


多进程是指操作系统能同时运行多个任务（程序）。
多线程是指在同一程序中有多个顺序流在执行。
每个正在系统上运行的程序都是一个进程。每个进程包含一到多个线程。进程也可能是整个程序或者是部分程序的动态执行。线程是一组指令的集合，或者是程序的特殊段，它可以在程序里独立执行。也可以把它理解为代码运行的上下文。所以线程基本上是轻量级的进程，它负责在单个程序里执行多任务。通常由操作系统负责多个线程的调度和执行。

在Java中，一个应用程序可以包含多个线程。每个线程执行特定的任务，并可与其他线程并发执行多线程使系统的空转时间最少，提高CPU利用率、多线程编程环境用方便的模型隐藏CPU在任务间切换的事实在Java程序启动时，一个线程立刻运行，该线程通常称为程序的主线程。

#### 主线程的重要性体现在两个方面：
1. 它是产生其他子线程的线程。
2. 通常它必须最后完成执行，因为它执行各种关闭动作。

### 三、进程与线程的区别：
-  （1）调度：线程作为调度和分配的基本单位，进程作为拥有资源的基本单位

-  （2）并发性：不仅进程之间可以并发执行，同一个进程的多个线程之间也可并发执行

-  （3）拥有资源：进程是拥有资源的一个独立单位，线程不拥有系统资源，但可以访问隶属于进程的资源.

-  （4）系统开销：在创建或撤消进程时，由于系统都要为之分配和回收资源，导致系统的开销明显大于创建或撤消线程时的开销。

### 同步和互斥的区别：
当有多个线程的时候，经常需要去同步这些线程以访问同一个数据或资源。例如，假设有一个程序，其中一个线程用于把文件读到内存，而另一个线程用于统计文件中的字符数。当然，在把整个文件调入内存之前，统计它的计数是没有意义的。但是，由于每个操作都有自己的线程，操作系统会把两个线程当作是互不相干的任务分别执行，这样就可能在没有把整个文件装入内存时统计字数。为解决此问题，你必须使两个线程同步工作。

      所谓同步，是指散步在不同进程之间的若干程序片断，它们的运行必须严格按照规定的某种先后次序来运行，这种先后次序依赖于要完成的特定的任务。如果用对资源的访问来定义的话，同步是指在互斥的基础上（大多数情况），通过其它机制实现访问者对资源的有序访问。在大多数情况下，同步已经实现了互斥，特别是所有写入资源的情况必定是互斥的。少数情况是指可以允许多个访问者同时访问资源。

        所谓互斥，是指散布在不同进程之间的若干程序片断，当某个进程运行其中一个程序片段时，其它进程就不能运行它们之中的任一程序片段，只能等到该进程运行完这个程序片段后才可以运行。如果用对资源的访问来定义的话，互斥某一资源同时只允许一个访问者对其进行访问，具有唯一性和排它性。但互斥无法限制访问者对资源的访问顺序，即访问是无序的。

### 进程间通信的方式？
1. 管道（pipe）及有名管道（named pipe）：管道可用于具有亲缘关系的父子进程间的通信，有名管道除了具有管道所具有的功能外，它还允许无亲缘关系进程间的通信。
2. 信号（signal）：信号是在软件层次上对中断机制的一种模拟，它是比较复杂的通信方式，用于通知进程有某事件发生，一个进程收到一个信号与处理器收到一个中断请求效果上可以说是一致的。
3. 消息队列（message queue）：消息队列是消息的链接表，它克服了上两种通信方式中信号量有限的缺点，具有写权限得进程可以按照一定得规则向消息队列中添加新信息；对消息队列有读权限得进程则可以从消息队列中读取信息。
4. 共享内存（shared memory）：可以说这是最有用的进程间通信方式。它使得多个进程可以访问同一块内存空间，不同进程可以及时看到对方进程中对共享内存中数据得更新。这种方式需要依靠某种同步操作，如互斥锁和信号量等。
5. 信号量（semaphore）：主要作为进程之间及同一种进程的不同线程之间得同步和互斥手段。
6. 套接字（socket）：这是一种更为一般得进程间通信机制，它可用于网络中不同机器之间的进程间通信，应用非常广泛。

### 进程和线程的关系：
1. 一个线程只能属于一个进程，而一个进程可以有多个线程，但至少有一个线程。
   
2. 资源分配给进程，同一进程的所有线程共享该进程的所有资源。

3. 处理机分给线程，即真正在处理机上运行的是线程。

4. 线程在执行过程中，需要协作同步。不同进程的线程间要利用消息通信的办法实现同步。线程是指进程内的一个执行单元,也是进程内的可调度实体.

### 多线程的优点
使用线程可以把占据时间长的程序中的任务放到后台去处理
用户界面可以更加吸引人，这样比如用户点击了一个按钮去触发某些事件的处理，可以弹出一个进度条来显示处理的进度
程序的运行速度可能加快
在一些等待的任务实现上如用户输入、文件读写和网络收发数据等，线程就比较有用了。在这种情况下可以释放一些珍贵的资源如内存占用等等。
多线程技术在IOS软件开发中也有举足轻重的位置。

### 多线程的缺点
如果有大量的线程,会影响性能,因为操作系统需要在它们之间切换。
更多的线程需要更多的内存空间。
线程可能会给程序带来更多“bug”，因此要小心使用。
线程的中止需要考虑其对程序运行的影响。
通常块模型数据是在多个线程间共享的，需要防止线程死锁情况的发生。
**总结:**
线程和进程的区别在于,子进程和父进程有不同的代码和数据空间,而多个线程则共享数据空间,每个线程有自己的执行堆栈和程序计数器为其执行上下文.多线程主要是为了节约CPU时间,发挥利用,根据具体情况而定. 线程的运行中需要使用计算机的内存资源和CPU。

## Android多线程编程

### 线程的基本用法
定义线程需要新建一个类来继承Thread，然后重写父类的run()方法，并在里面编写耗时逻辑
```kotlin
class MyThread:Thread(){//继承一个线程类来使用,但是这种方式耦合度太高了
    override fun run() {
        super.run()
        println("run MyThread by Thread")
    }
}
fun main(){
    MyThread.start()
}
```
一般会采用实现接口
```kotlin
class MyThread:Runnable{
    override fun run() {
        println("run MyThread by Runnable")
    }
}
fun main(){
    val myThread=MyThread()
    Thread(myThread).start()
}
```
如果不想专门写一个类去实现Runnable度的话可以使用lambda的方式
fun main(){
    Thread{
        run {
            println("run MyThread by Runnable")
        }
    }.start()
}
kotlin 还有一种更加简单的开启线程方式----顶层方法开启
```kotlin
fun main(){
    thread {
        println("run MyThread by Runnable")
    }
}
```

### 在子线程中更新UI
和其他的很多GUI库一样,Android的UI也是线程不安全的，也就是说，如果想要更新应用程序里面的UI元素,就必须在主线程中进行，否则就会出现异常
```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val updateText=1//updateText=1就标志这可以进行更新ViewText

    //注意handleMessage()是在主线程里面的
    private val handler=object:Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what){
                updateText->binding.textView.text="草泥马世界"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeTextBtn.setOnClickListener {
            /*thread { //在点击事件中开启了一个子线程，会报错Only the original thread that created a view hierarchy can touch its views
                //Android是不允许子线程去进行UI操作的
                binding.textView.text="草泥马世界"
            }*/

            /*但是Android提供了一种异步消息处理机制，完美的解决在子线程中进行UI操作的问题*/
            thread {
                val msg=Message()
                msg.what=updateText
                handler.sendMessage(msg)//把消息对象发送出去

                /*以上的流程:
                我们并没有直接在子线程中直接进行UI操作
                而是在子线程中创建了一个Message()消息对象,并将他的what属性指定为updateText
                然后调用Handle的sendMessage()方法将这条Message发送出去
                并且位于主线程中的handleMessage()方法接收到这条Message，判断要更改UI，就会更改了*/
            }
        }
    }
}
```

### 解析异步消息处理机制
android的异步消息处理机制主要由四部分
- `Message`
  ```kotlin
    Message是在线程之间传递的消息,他的内部会携带少量的消息，用于在不同线程中传递数据
    - what 字段 
    - arg1和arg2 可以携带一些整型数据
    - obj 字段 可以携带一个object对象
  ``
- `Handler`
  ```kotlin
    处理者:主要负责发送和处理消息
    发送消息一般是使用handler的sendMessage()和post()方法等
    发送到阿消息经过一系列的处理后最终会回到Handler的handleMessage()方法中
  ```
- `MessageQueue`
  ```kotlin
    消息队列：主要存储所有通过Handler发送的消息
    这部分消息会一直存在于消息队列里面，等待被处理
    每个线程都会有一个MessageQueue对象
  ```
- `Looper`
  ```kotlin
    每个线程的MessageQueue中的管家
    调用Looper中的loop()方法后就会进入一个无限循环中，每当发现MessageQueue存在一条消息,就会把他取出，传递到
    Handler的handlerMessage()方法中,每个线程都有一个Looper对象
  ```
**异步处理消息的流程**
1. 首先在需要在主线程里面创建一个`Handler`对象，并且重写`handleMassage()`帆帆发
2. 当需要子线程做想要做，但是做不了的逻辑时，就会创建一个`Message`对象，并且通过`Handler`中的`sendMessage()`方法把消息发送出去
3. 消息会被添加到消息队列`(MessageQueue)`中等待处理
4. `Looper`会从消息队列里面把消息取出来
5. 然后`Looper`把消息分发会`Handler`中
6. 由于`Handle`是在主线程中创建的，所以`handleMessage()`里面的阿逻辑执行时也是在主线程中
[![bOULkT.png](https://s1.ax1x.com/2022/03/14/bOULkT.png)](https://imgtu.com/i/bOULkT)

### 使用`AsyncTask`
`Android`为了方便在子线程中使用`UI`操作，提供了一些好用的工具，比如:`AsyncTask`，可以简单的从子线程切换到主线程，`AsyncTask`也是基于异步消息处理机制
#### 基本用法
`AsyncTask`是一个抽象类，所以需要继承后才能使用，继承时对`AsyncTask`提供三个泛型参数
- `Params`:在执行`AsyncTask`时需要传入的参数，可用于在后台任务中使用
- `Progress`:在后台执行任务的时候，如果需要在界面上显示当前的进度，则使用这里指定的泛型作为进度单位
- `Result`:当任务执行完毕后,如果需要对结果进行返回，则使用这里指定的泛型作为返回值类型
- 比如
  - 代码
  ```kotlin
    class AsyncTaskTest() : AsyncTask<Unit, Int, Boolean>() {
        /*第一个参数是Unit说明不需要传入参数给后台任务
        第二个参数是Int说明进度单位是Int
        第三个参数是Boolean说明用布尔型数据反馈执行结果*/
        ···
    }
  ```
  - 目前AsyncTaskTest还是一个空任务，我们需要重写4个方法才能完成对任务的定制
    - `onPreExecute(Params...)`
      ```kotlin
        这个方法会在后台任务开始执行之前调用，用于进行一些界面上的初始化操作，比如显示进度条对话框啥的
      ```
    - `doInBackground(Progress...)`
      ```kotlin
        这个方法的所有代码都会在子线程中执行，我们应该在这里去处理所有的耗时任务，任务一旦完成就可以通过return将任务的运行结果返回
        如果AsyncTask的第三个参数是Unit就不需要返回执行结果
        在这个方法里面是不能进行UI操作的
        如果要更新UI元素，比如反馈当前的任务执行程度，需要调用publishProgress(Pregress...)方法完成
      ```
    - `onProgressUpdate(Progress...)`
      ```kotlin
        当后台任务中调用了publishProgress(Pregress...)方法后,onProgressUpdate(Progress...)很快就会被调用,该方法携带的参数就是在后台任务中传递过来的，
        这个方法中可以对UI元素操作，利用参数中的数值对界面元素更新
      ```
    - `onPostExecute(Result)`
      ```kotlin
        在后台任务执行完毕并且通过return语句进行返回，这个方法很快就会被调用，返回的数据会作为参数传递到此方法中
        可以利用返回的数据进行一些UI操作，比如提醒任务执行的结果，以及关闭进度条对话框等
      ```
  - 一个比较完整的自定义`AsyncTask`可以写成这样
    ```kotlin
    class AsyncTaskTest() : AsyncTask<Unit, Int, Boolean>() {
        /*第一个参数是Unit说明不需要传入参数给后台任务
        第二个参数是Int说明进度单位是Int
        第三个参数是Boolean说明用布尔型数据反馈执行结果*/
        override fun doInBackground(vararg p0: Unit?): Boolean =try{
            while (true){
                val down=doDown()//这是一个虚构的方法，计算当前的下载进度
                onProgressUpdate(down)
                if (down>=100){//下载进度到达100
                    break
                }
            }
            true
            }catch (e:Exception){
                e.printStackTrace()
                false
            }

        override fun onPreExecute() {
            progressDialog.show()//显示进度条
        }

        override fun onPostExecute(result: Boolean?) {
            progressDialog.dismess()//关闭对话框
        }

        override fun onProgressUpdate(vararg values: Int?) {
            //在这里进行下载
            progressDialog.setMessage("Download ${values[0]}%")
            //在这里显示下载结果
            if(result){
                Toast.makeText(context,"下载成功",Toast.LENGTH_SHORT)
            }else{
                Toast.makeText(context,"下载失败",Toast.LENGTH_SHORT)
            }
            
        }
    }
    ```
   - 使用`AsyncTask`的诀窍
      - `doInBackground()`中执行具体的耗时任务
      - `onProgressUpdate()`中执行UI操作
      - `onPostExecute()`中执行任务的收尾工作
      - 想要启动这个任务只需要编写`AsyncTaskTest.execute()`，而且可以在`execute()`中传入任意数量的参数，这写参数对传递到`doInBackground`烦恼方法中

## `Service`的基本用法
四大组件之一的`Service`准备好了吗?我来咯

### 定义一个`Service`
```xml
创建完service后是需要在AndroidManifest.xml中注册的(四大组件都要注册)

<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.bo.a2_learnservice">

    <application
        ···>
        <service
            android:name=".MyService"
            android:enabled="true"
            android:exported="true">
            <!--android:exported这个Service能不能被其他外部程序调用-->
        </service>
        ···
    </application>

</manifest>
```
service使用
```xml
布局
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    ···>

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/startService"
        android:text="开启服务"/>

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/stopService"
        android:text="关闭服务"/>

</LinearLayout>
```
```kotlin
class MainActivity : AppCompatActivity() {

    ···

    override fun onCreate(savedInstanceState: Bundle?) {
        ···

        //service也是用Intent操作的
        binding.startService.setOnClickListener {
            val intent=Intent(this,MyService::class.java)
            startService(intent)
        }

        binding.stopService.setOnClickListener {
            var intent=Intent(this,MyService::class.java)
            stopService(intent)
        }
        /*这里开启服务和关闭服务都是定义在Context类中所以Activity可以用
        除了在Activity中关闭其实Service可以自行关闭:在Service中调用stopSelf()即可*/
    }
}
```
```kotlin
class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    /*onCreate，onStartCommand,onDestroy这三个方法是每个Service中最常用到的*/

    override fun onCreate() {//在Service创建的时候调用
        super.onCreate()
        Log.d("MyService","创建了服务")
    }

    /*onstart()方法和onStartCommand()方法的区别：
    onstart()方法是在android2.0一下的版本中使用。而在android2.0以上则使用onstartCommand()方法。
    它们两个方法放在一起使用时，不会产生冲突。*/
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {//在每次Service启动的时候调用
        Log.d("MyService","开启了服务")
        return super.onStartCommand(intent, flags, startId)
        /*通常状况下
        如果我们希望Service一旦启动就执行某个逻辑，那么这个逻辑就写在onStartCommand()里面*/
    }
    /*onCreate()只有在第一次创建的时候调用，onStartCommand每次开启都会调用*/

    override fun onDestroy() {//销毁Service时回收不再使用的资源
        Log.d("MyService","销毁了服务")
        super.onDestroy()
    }
}
```

### `Activity`和`Service`进行通信
`onBind()`方法可以帮助Activity去指挥Service做事
**比如我们希望MyService可以提供一个下载功能，然后在Activity中决定何时开始下载，并且可以监控下载进度**
```kotlin
MyService

class MyService :Service(){
    private val mBinder=DownloadBinder();

    inner class DownloadBinder:Binder(){
        fun startDownload(){
            Log.d("MyService","开始下载")
        }

        fun getProgress():Int{
            Log.d("MyService","下获取下载进度中")
            return 0
        }

        fun stopDownload(){
            Log.d("MyService","停止下载")
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }
    ···
}
```
```kotlin
MainActivity

class MainActivity : AppCompatActivity() {

    ···
    private lateinit var downloadBinder:MyService.DownloadBinder

    private val connection=object:ServiceConnection{//只有在Activity和Service成功绑定的时候调用
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        downloadBinder=service as MyService.DownloadBinder//由IBinder向下转型变成MyService.DownloadBinder
        downloadBinder.startDownload()
        downloadBinder.getProgress()
    }

        override fun onServiceDisconnected(name: ComponentName?) {//只有在服务创建进程奔溃或者被杀掉的时候才会被杀掉
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bindService.setOnClickListener {
            val intent=Intent(this,MyService::class.java)
            bindService(intent,connection,Context.BIND_AUTO_CREATE)
            //Context.BIND_AUTO_CREATE表示如果没有服务就自动创建一个服务，
            // 然后把this，也就是MainActivity和intent中指定的服务建立connection联系
            //此处会发现这样创建服务的时候只调用了onCreate(),并没有调用onStartCommand()
            //而且我们会发现这样创建的服务调用了只有先解绑了服务才能正常调用stopService(),正常销毁伏虎
        }

        binding.unbindService.setOnClickListener {
            downloadBinder.stopDownload()
            unbindService(connection)//解绑服务
        }

        ···
    }
}
```
**这里如果我强行把在后台中运行的Service杀掉，会先调用onCreate()然后才调用onDestory()，我也不知道为什么?我好想知道为什么，呜呜呜,但是使用前台Service就没有这样的状况**


## `Service`的生命周期

##### 一，在Service的生命周期中，被回调的方法比Activity少一些，只有以下五种：
- `onCreate`
- `onStartCommand`
- `onDestroy`
- `onBind`
- `onUnbind`

##### 二，通常有两种方式启动一个Service,他们对Service生命周期的影响是不一样的。
1. 通过`startService`
   ```kotlin
    Service会经历 onCreate 到onStart，然后处于运行状态，stopService的时候调用onDestroy方法。
    如果是调用者自己直接退出而没有调用stopService的话，Service会一直在后台运行。
    ```
2. 通过`bindService`
    ```kotlin
    Service会运行onCreate，然后是调用onBind， 这个时候调用者和Service绑定在一起。调用者退出了，Srevice就会调用onUnbind->onDestroyed方法。
    所谓绑定在一起就共存亡了。调用者也可以通过调用unbindService方法来停止服务，这时候Srevice就会调用onUnbind->onDestroyed方法。
    ```

三，需要注意的是如果这几个方法交织在一起的话，会出现什么情况呢？
一个原则是`Service`的`onCreate`的方法只会被调用一次，就是你无论多少次的`startService`又`bindService`，`Service`只被创建一次。

1. 如果先是`bind`了，那么`start`的时候就直接运行`Service`的`onStart`方法，如果先是`start`，那么`bind`的时候就直接运行onBind方法。
<br>
2. 如果`service`运行期间调用了`bindService`，这时候再调用`stopService`的话，`service`是不会调用`onDestroy`方法的，`service`就`stop`不掉了，只能调用`UnbindService`,` service`就会被销毁。
<br>
3. 点击`unbindService`的时候，如果之前点击了`stopService`，此时会直接执行`service`的`onDestroy`方法。如果之前没点击`stopService`，则只会执行`unbindService`
<br>
4. 如果一个`service`通过`startService` 被`start`之后，多次调用`startService `的话，`service`会多次调用`onStart`方法。多次调用`stopService`的话，`service`只会调用一次`onDestroyed`方法。
   <br>    
5. 如果一个`service`通过`bindService`被`start`之后，多次调用`bindService`的话，`service`只会调用一次`onBind`方法。
<br>
6. 多次调用`unbindService`的话会抛出异常

## `Service`的更多技巧

### 使用前台`Service`
- 从`android8.0`开始，只有应用保持在前台可见的状态下，`Service`才能保证稳定运行，一旦进入后台，`Servcie`就随时有可能会被系统回收，而我希望能一直让`Service`一直保持运行状态就可以考虑使用前台`Service`了
- 前台Service和不同Service最大的区别就是前台Service会有一个正在运行的图标在系统的状态栏里显示，下拉后能看见更加详细的内容
- 由于状态栏有一个正在运行的图标，那么系统就不会倾向于回收前台Service，而且用户能清晰的看到应用在运行，也就不能存在恶意应用在后台偷跑资源的情况了
```kotlin
class MyService :Service(){
    ···
    override fun onCreate() {//在Service创建的时候调用
        super.onCreate()
        Log.d("MyService","创建了服务")

        val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel=NotificationChannel("my_service","前台Service",NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val intent=Intent(this,MainActivity::class.java)
        val pi=PendingIntent.getActivity(this,1,intent,0)
        val notification=NotificationCompat.Builder(this,"my_service")
            .setContentIntent(pi)
            .setContentTitle("前台Service标题")
            .setContentText("前台Service内容")
            .setSmallIcon(R.drawable.small_icon)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.large_icon))
            .build()
        startForeground(1,notification)//开启一个前台
    }
}
```

### 使用`IntentService`
`Service`中的代码是默认运行在主线程里面的，当直接在`Service`中处理一些耗时的逻辑时就很容易出现`ANR(Application Not Responding)`(程序无响应)，那么这里就可以用到多线程编程了
```kotlin
class MyService :Service(){
    ···
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread { 
            //具体的逻辑
            Log.d("MyService","开启了服务")
            
            //如果想要Service执行完后自动停止
            stopSelf()
        }
        
        return super.onStartCommand(intent, flags, startId)
    }
}
```
**这种Service一旦启动，就会一直处于运行状态**
**必须调用stopService()或者stopSelf()或者被系统回收，Service才会停止**

#### android提供了一个IntentService类来帮助程序员开启线程和关闭线程Service
```kotlin
创建一个MyIntentService来继承IntentService

class MyService :Service(){
    ···
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            //这种Service一旦启动，就会一直处于运行状态
            //必须调用stopService()或者stopSelf()或者被系统回收，Service才会停止
            //具体的逻辑
            Log.d("MyService","开启了服务")

            //如果想要Service执行完后自动停止
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }
}
```
```kotlin
class MainActivity : AppCompatActivity() {
    ···
        binding.startIntentService.setOnClickListener {
            Log.d("MainActivity","当前现成的id是 ${Thread.currentThread().name}")
            var intent=Intent(this,MyIntentService::class.java)
            startService(intent)
        }
    }
}
```
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    ···>
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/startIntentService"
        android:text="开启MyIntentService"/>

</LinearLayout>
```
```xml
别忘了四大组件都是要注册的

<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.bo.a2_learnservice">

    <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
    <application
       ···>
        <service
            android:name=".MyIntentService"
            android:enabled="true"
            android:exported="true">
            <!--android:exported这个Service能不能被其他外部程序调用-->
        </service>
        ···
    </application>
</manifest>
```