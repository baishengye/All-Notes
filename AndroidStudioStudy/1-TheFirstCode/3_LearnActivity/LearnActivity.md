# Activity的基本使用
## 从0开始创建一个Activity
1. 首先需要创建一个基础的FirstActivity
2. 创建一个layout
   类型是LinearLayout
    ```xml
    <!--    给页面中添加一个按钮-->
    <!-- 在first_layout.xml中添加代码 -->
    <!--    从上到下的含义依次是:-->
    <!--    id:这个按钮的唯一标识符,我们在代码里就是用这个找到按钮-->
    <!--    layout_width:指定按钮的宽度,match_parent(和父亲一样宽)-->
    <!--    layout_height:指定按钮的高度,wrap_content(刚好能包含按钮中的内容)-->
    <!--    text:按钮中的文本-->
    <Button
        android:id="@+id/button1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button 1"/>
    ```
3. 然后想要调用first_layout.xml
   ```kotlin
    //就需要去到Activity中的onCreate取引入这个布局
    setContentView(R.layout.first_layout)//在界面中引入这种布局
    //        在这里我们是直接引入的一个布局，但是我们一般会传入布局文件的id
    //        流程是:
    //        在项目中添加的资源都会在R文件中生成一个相应的id
    //        比如这里调用R.layout.first_layout就得到了first_layout.xml的id，然后就把id传到了setContentView()中
   ```
4. 想要Activity能够在app中生效还需要配置`AndroidManifest.xml`
   ```xml
   <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.bo.a3_learnactivity"
        android:versionCode="1"
        android:versionName="1.0" >

        <uses-sdk
            android:minSdkVersion="21"
            android:targetSdkVersion="30" />

        <!-- Activity的注册要放在<application/>中 -->
        <!-- 当我们创建FirstActivity的时候必须要在AndroidManifest.xml声明 -->
        <application
            android:allowBackup="true"
            android:appComponentFactory="androidx.core.app.CoreComponentFactory"
            android:debuggable="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:testOnly="true"
            android:theme="@style/Theme.3_LearnActivity" >

            <!-- android:name指定具体注册哪个Activity -->
            <!-- 但是此处的.FirstActivity是何含义? -->
            <!-- 其实是com.bo.a3_learnactivity.FirstActivity，只不过是最外层的<manifest>标签通过package指定了程序的包名是com.bo.a3_learnactivity -->
            <!-- 所以就只用写.FirstActivity -->
            <!-- android:label指定的是程序标题栏的内容 -->
            <activity
                android:name=".FirstActivity"
                android:exported="true"
                android:label="This a FirstActivity" >
            <!-- 但是到这里还不能运Activity因为还没有指定哪个Activity先启动 -->
            <!-- 得添加两句代码 -->
            <intent-filter>

                <!-- 把FirstActivity标记成主程序,首先打开的就是他 -->
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            </activity>
        </application>

    </manifest>     
    ```

## Toast使用
`Toast是Android中提供的一种提醒方式,程序中可以使用他将一些短小的信息通知给用户，这些信息会在一段时间后自动消失,和js中的alert()效果一样`
```kotlin
//需要定义一个弹出Toast的触发点,可以把按钮当作触发点
var button1: Button = findViewById(R.id.button1)//R.id.button1获取到button1的id
button1.setOnClickListener{
    Toast.makeText(this,"你点击了按钮",Toast.LENGTH_SHORT).show()
}
```
```kotlin
1. findViewById()方法获取到对应id的泛型对象(继承自View)，所以kotlin是无法推断出他返回的到底是什么,所以需要显式定义
2. setOnClickListener()函数为按钮注册了一个监听器,监听到点击按钮，会执行监听器中的OnClick方法
3. makeText(context,text,时长)是一个静态方法，创建一个Toast，但是这需要三个参数
    context: Toast需要的上下文，Activity本身是一个Context对象,所以此处传入this
    text:文本内容
    时长:Toast.LENGTH_SHORT(短时间),Toast.LENGTH_LONG(长时间)
```
```kotlin
//    关于findViewById()函数
//    findViewById()作用是获取布局文件中控件的实例，本次中只有1个，那如果有10个呢，那就需要10个findViewById()
//   所以有了ButterKnife之类的第三方库
//   但是我们引入过插件kotlin-android-extensions,这个插件会根据布局文件自动生成一个具有相同名称的变量，我们就可以直接在Activity中使用这个变量
//                所以以上代码可简化
        button1.setOnClickListener{
            Toast.makeText(this,"你点击了按钮",Toast.LENGTH_SHORT).show()
        }
```

## activity中使用menu
1. res下面需要一个menu文件夹,下面新建一个menu文件(Menu resource file)
    ```xml
    menu中
    <!--    用item标签创建具体的菜单项-->
    <!--    android:id:给这个菜单项一个唯一的id-->
    <!--    android:title:给菜单项指定一个名称-->
    <item
        android:id="@+id/add_item"
        android:title="Add"/>
    <item
        android:id="@+id/remove_item"
        android:title="Remove"/>
    ```
2. 
   ```kotlin
    //想要创建Menu就要重写onCreateOptionsMenu函数
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        /*menuInflater在这儿里其实是调用了父类的getMenuInflater()方法,
        getMenuInflater()会得到一个menuInflater对象,在调用它的inflate(),就创建了一个菜单
        inflate()有两个参数:
            R.menu.main:指明要传入的资源文件，根据这个文件来创建菜单
            main:指明我们的菜单项将添加到哪个Menu对象中,这里直接使用onCreateOptionsMenu()传来的Menu
            return:false(不能显示),true:(可以显示)*/
        return true
    }
   ```
3. 
   ```kotlin
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){//item.itemId获取Menu中Item的id，
            R.id.add_item->Toast.makeText(this,"add",Toast.LENGTH_SHORT).show()
            R.id.remove_item->Toast.makeText(this,"remove",Toast.LENGTH_SHORT).show()
        }
        //Menu和里面的菜单项都不需要用代码显式的去监听,Menu中的菜单项已经自动被监听了
        return true
    }
   ```
   
## 销毁一个Activity
```c++
//代码销毁Activity
 //finish()函数用来销毁Activity
button1.setOnClickListener{finish()}
```

# 使用Intent在Activity之间穿梭
```kotlin
intent是Android程序中各组件之间进行交互的重要方式，它不仅可以指明当前组件想要执行的动作，还可以在不同组件之间传递数据
Intent:一般用于
    启动Activity
    启动Service
    发送广播等等场景
Intent:显式Intent，隐式Intent
```
## 显式Intent
```kotlin
Intent有多个构造函数的重载,其中一个是 Intent(Context packageContext,Class<?> cls)
Context: 提供一个启动Activity的上下文
Class<?>: 用于指明想要启动的目标Activity
Activity类中提供了一个startActivity()方法，专门用来启动Activity，他接受一个Intent参数，启动Activity
比如:在FirstActivity的onCreate()中写

button1.setOnClickListener{
    var intent= Intent(this,SecondActivity::class.java)
         startActivity(intent)
}
//点击button1就会从FirstActivity中打开SecondActivity
```
## 隐式Intent
```kotlin
他不明确指出想要启动哪个Activity
而是指定了一系列更加抽象的action,category等信息
交给系统分析这个intent，找出最合适的Activity
```
```kotlin
什么叫合适的Acitivity?其实想要隐式Intent还需要一些配置
在AndroidManif.xml中的Activity标签下配置

<activity
    android:name=".SecondActivity"
    android:exported="true"
        android:label="This a SecondActivity">
        <intent-filter>
            <!-- <action>指明了当前这个Activity可以响应com.bo.a3_learnactivity.ACTION_START这个action-->
            <action android:name="com.bo.a3_learnactivity.ACTION_START"/>
            <!-- <category>包含一些附加信息,更加精确的指明了category能够响应的Intent中可能带有的category-->
            <category android:name="android.intent.category.DEFAULT"/>
            <!-- 只有<action>和<category>中的内容同时匹配Intent中的action和category，这个Activity才能响应Intent-->
        </intent-filter>
</activity>
```
```kotlin
但这还不够
要设置好Intent
button1.setOnClickListener{
    var intent= Intent("com.bo.a3_learnactivity.ACTION_START")//action
        //intent.addCategory("android.intent.category.DEFAULT")//category:其实"android.intent.category.DEFAULT"的可以不用写，因为Intent中默认的category就是这个
         startActivity(intent)//这个函数调用的时候把默认的cagegory添加到intent中
}
//这里Intent使用了另一种构造方法
```
```kotlin
Intent虽然只能指定一个Action但是可以指定多个category
button1.setOnClickListener{
    var intent= Intent("com.bo.a3_learnactivity.ACTION_START")
    intent.addCategory("com.bo.a3_learnactivity.MY_CATEGORY")
    //这里intent除了默认的category还有我们自定义的category，我们添加了一个自定义的category，就要在AndroidManifest.xml中加上，不然响应不了
    startActivity(intent)
}
xml:  <category android:name="com.bo.a3_learnactivity.MY_CATEGORY"/>
```

## 更多隐式Intent用法
```kotlin
Intent不仅可以启动自己程序内的Activity，还可以启动其他程序的Activity
这就使多个应用程序之间的功能共享成为了可能
比如程序需要打开浏览器，只需要调用系统的浏览器即可

button1.setOnClickListener{
    var intent= Intent(Intent.ACTION_VIEW)
    intent.data=Uri.parse("http://www.bilibili.com")
    //这里intent除了默认的category还有我们自定义的category，我们添加了一个自定义的category，就要在AndroidManifest.xml中加上，不然响应不了
    startActivity(intent)
}
//首先指明的Action是Intent.ACTION_VIEW,这是Android系统中的一个内置动作,其常量值是android.intent.action_VIEW.
//然后通过Uri.parse()将一个网址字符串解析成Uri对象
//再调用Intent的setData()方法将这个Uri对象传递出去，给到intent里面
```
```kotlin
还可以在AndroidManifest.xml中的<intent-filter>标签中再配置一个<data>标签
用于更精确的指定当前Activity能够响应的数据
android:sheme: 用于指定数据的协议部分，如https
android:host: 用于指定数据的主机名部分,如www.bilibili.com
android:port: 用于指定数据的端口部分，一般紧随再主机名后，比如:localhost:8080中的8080
android:path: 用于指定主机名和端口之后的部分，如一段网址中跟在域名之后的内容
android:mimeType: 用于指定可以处理的数据类型，允许使用有通配符的方式进行指定
只有<data>标签中内容和Intent携带的Data完全一致时，Activity才能响应Intent，不过一般不会把所有的都写上，比如只指定了android:sheme: 为https就可以响应所有https协议的Intent了
```
**实例**
```kotlin
xml:
<activity
     android:name=".ThirdActivity"
    android:exported="true" >
    <intent-filter tools:ignore="AppLinkUrlError">
        <action android:name="android.intent.action.VIEW"/>
        <category android:name="android.intent.category.DEFAULT"/>
        <data android:scheme="https"/>
    </intent-filter>
</activity>

kt:onCreate():
button1.setOnClickListener{
    var intent= Intent(Intent.ACTION_VIEW)
    intent.data=Uri.parse("https://www.baidu.com")
    //这里intent除了默认的category还有我们自定义的category，我们添加了一个自定义的category，就要在AndroidManifest.xml中加上，不然响应不了
    startActivity(intent)
}
但是还是不要瞎搞，以免用户体验差
```
```kotlin
除了https协议,我们还可以指定很多其他的协议
比如:geo表示地理位置,tel表示拨打电话

button1.setOnClickListener{
    var intent=Intent(Intent.ACTION_DIAL)//android系统内置动作
    intent.data=Uri.parse("tel:10086")//指明了协议是tel，号码时10086
    startActivity(intent)
}
```

## 向下一个Activity传递数据
```kotlin
其实Intent在启动Activity的时候还可以传递数据
Intent提供了一系列的putExtra()方法的重载,
可以把想要传递的数据暂存在intent中，在启动Activity的时候还可以传递数据

比如FirstActivity希望点击按钮就把一个字符串传递到SecondActivity中

FirstActivity传递数据到SecondActivity:
button1.setOnClickListener {
    var intent=Intent(this,SecondActivity::class.java)
    intent.putExtra("extra_data","Hello SecondActivity")//添加数据到intent中
    startActivity(intent)
}

SecondActivity接受数据:
/*这里intent实际上是使用的父类的getIntent()方法,该方法会获取用于启动SecondActivity的Intent
然后调用getStringExtra()获取字符串类型的数据*/
var extraData=intent.getStringExtra("extra_data")
Log.d("SecondActivity","FirstActivity say $extraData")
```

## 携带数据返回给上一个Actiity
```kotlin
其实想要返回到上一个Activity只需要一个back而已，
那么这样没有用到Intent了,而就无法返回数据了

那么其实Activity类中还有一个用于启动Activity的方法startActivityForResult(),
他期望在Activity销毁的时候能够返回一个结果给上一个Activity。
```
```kotlin
startActivityForResult(intent:Intent,class<?>)中有两个参数
第一个还是Intent
第二个是请求码，用来在之后的回调中判断数据的来源
```
**实例**
```kotlin
FirstActivity给SecondActivity一个唯一的请求码
button1.setOnClickListener{
    var intent=Intent(this,SecondActivity::class.java)//上下文,响应Activity
    startActivityForResult(intent,1)//请求码只要是一个唯一值就行了，所以这里传的1
}

SecondActivity在销毁的时候返回信息，他销毁后会回调上一个
Activity的onActivityResult()函数接受数据
button2.setOnClickListener {
    var intent=Intent()//这个Intent只是为了携带数据，不具有指定Activity的功能
    intent.putExtra("return_data","SecondActivity say Bey FirstActivity")//返回数据的名称,返回数据里的内容
    setResult(RESULT_OK,intent)
    /*setResult()专门用于向上一个Activity返回数据,他会接受2个参数:
        第一个参数用于向上一个Activity返回处理结果,一般使用RESULT_OK或者RESULT_CANCELED
        第二个参数则是把带有数据的Intent传递回去*/
    finish()
}

FirstActivity需要重写onActivityResult方法来接收信息
//由于startActivityForResult()方法来启动SecondActivity
//所以SecondActivity被销毁之后会回调上一个Activity的onActivityResult()，所以在此需要重写FirstActivity的onActivityResult()方法
//SecondActivity返回的数据在onActivityResult()中接收到
/*onActivityResult有三个参数:
    requestCode: 启动Activity时传入的请求码
    resultCode: 我们返回数据时的处理结构:成功还是失败或者其他
    data: 携带数据的intent
由于一个Activity可能会调用startActivityForResult()去启动很多个不同的Activity
每个Activity返回的数据都会自动回调到onActivityResult()中，
所以我们需要判断requestCode知道这个数据是从哪个Activit返回的
然后再通过resultCode判断是否成功处理数据
再对数据进行操作*/
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when(requestCode){
        1->if(resultCode == RESULT_OK){
            var returnedData=data?.getStringExtra("return_data")
            Log.d("FirstActivity","$returnedData")
        }
    }
}
```
**如果用户不是按钮销毁Activity而是back按钮销毁**
```kotlin
这样就需要重写SecondActivity中的onBackPressed()方法
override fun onBackPressed() {
    var intent=Intent()
    intent.putExtra("return_data","SecondActivity say Bey FirstActivity")
    setResult(RESULT_OK,intent)
    finish()
}
```

# Activity的生命周期(超级重要)
## 返回栈
```kotlin
Activity是可以叠加的，新生成的会在旧的之上
android是使用任务(task)来管理Activity的
一个任务(task)就是一组放在栈(stack)中的Activity的集合
销毁一个Activity的时候其实就是最上面的Activity出栈了
系统总是把栈顶元素给用户看
```
## Activity状态
**Activity最多有4个状态**
- 运行状态
  在栈顶，正在给用户看，系统最不愿意回收的就是运行状态的Activity,用户体验很差
- 暂停状态
  不在栈顶，暂停运行，但仍然可见，因为不是每个Activity都会把屏幕占满，比如对话框，只有内存很低时系统才愿意回收
- 停止状态
  不在栈顶，停止运行，不可见，但是系统仍然给他保留了相应的状态和成员变量，而就是唤醒后能恢复到原来状态，他是很有可能被系统回收内容的
- 销毁状态
  已经出栈了，销毁了，系统最倾向与回收这种状态的Activity，保证内存充足

## Activity的生存期
**Activity类中定义了7个回调方法。，覆盖了Activity生命周期的每一个环节**
- ```kotlin
    onCreate:
    每个Activity都会重写，会在Activity第一次创建的时候调用，
    可以在之内完成Activity的基本工作:加载布局,绑定事件...
    ```
- ```kotlin
    onStart()
    在Activity由不可见变为可见的时候调用
    ```
- ```kotlin
  onResume()
  Activity准备好和用户交互的时候调用,此时的Activity一定是在栈顶，
  并且是运行状态
  ```
- ```kotlin
    onPause()
    系统准备去启动或者回复另一个Activity的使用调用，
    通常是在这个里面把一些消耗掉的CPU资源释放掉，以及保存一些关键数据，
    但是这个方法的执行速度一定要快，不然会影响到新的栈顶Acrivity的使用
  ```
- ```kotlin
    onStop()
    Activity完全不可见的时候调用，他和OnPause()的区别在于，
    如果启动新Activity是一个对话框式的Activity，那么onPause()就会执行，而onStop()不执行
  ```
- ```kotlin
    onDestroy()
    在Activity销毁前调用，之后Activity就会成为销毁状态
    ```
- ```kotlin
    OnRestart()
    Activity由停止状态变成运行状态之前调用，也就是Activity被重新启动了
  ```

**以上的7个方法除了onRestart()之外，其他6个都是两两相对的那么又可以把Activity分成3个生存期**
- 完整生存期
  Activity从onCreate()到OnDestroy()之间所经历的就是完整生存期;一般情况下，Activity会在onCreate()中完成各种初始化操作，onDestory()中完成释放内存的操作
- 可见生存期
   Activity从onStart()到OnStop()之间所经历的就是可见生存期;在这个过程中Activity总是对用户可见的，即使可能无法与用户交互，但是可以用这两个方法合理的管理哪些对用户可见的资源。比如在onStart()方法中对资源进行加载，而在onStop中对资源进行释放，从而保证处于停止状态的Activity不会占用过多内存
- 前台生存期
  Activity从OnResume()到到OnPause()之间所经历的就是前台生存期;前台生存期总是处于运行状态，此时的Activity是可以和用户交互的，平时我们接触最多的就是这个时期
  [![bwMb7Q.md.png](https://s4.ax1x.com/2022/03/05/bwMb7Q.md.png)](https://imgtu.com/i/bwMb7Q)

## 实例
```kotlin
需要三个Activity:
    MainActivity:主活动
    NormalActivity:一般活动
    DialogActivity:对话框活动
```
**创建流程**
```kotlin
normal_layout.xml:

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="This id a normal activity"/>
</LinearLayout>

dialog_layout.xml:

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="This is dialog activity"/>
</LinearLayout>

把DialogActivity定义成对话框式Activity:
<activity
    android:name=".DialogActivity"
    android:exported="true"
    android:theme="@style/Theme.AppCompat.Dialog">
    <!-- 上面一行是声明这个Activity是对话框主题的-->
</activity>

MainActivity的onCreate():

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity","onCreate")
        setContentView(R.layout.activity_main)

        startNormalActivity.setOnClickListener {
            var intent=Intent(this,NormalActivity::class.java)
            startActivity(intent)
        }

        startdialogActivity.setOnClickListener {
            var intent=Intent(this,DialogActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity","onRestart")
    }
}

activity_main.xml:

<Button
    android:id="@+id/startNormalActivity"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Start NormalActivity"/>

<Button
    android:id="@+id/startdialogActivity"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Start DialogActivity"/>
```
**效果分析**
```kotlin
MainActivity被创建的时候输出
onCreate
onStart
onResume

点击startNormalActivity的时候输出
onPause
onStop
因为NormalActivity已经完全把MainActivity覆盖,MainActivity不在栈顶了，因此onPause和onStop被执行，MainActivity进入了停止状态

点击back，输出
onRestart
onStart
onResume
之前MainActivity进入了停止状态，所以onRestart会得到执行，之后会依次执行onStart和onResume,但是onCreate不会被执行，因为MainActivity并没有被重新创建

点击startDialogActivity
onPause
只有onPause得到了执行，onStop并没有被执行,这是因为DialogActivity并没有完全遮挡住MainActivity，此时MainActivity只是进入了暂停状态

点击back
onResume
MainActivity从暂停状态唤醒，就只会调用onResume方法

点击back退出MainActivity
onPause
onStop
onDestroy
然后依次执行这三个方法，销毁Activity
```

## Activity被回收了怎么办
```kotlin
当Activity进入停止状态的时候，是有可能被系统回收的，比如:
当前有一个Activity A
用户在A的基础上启动了Activity B
这个时候A就进入了停滞状态,这个时候如果系统内存不足，就将A回收了,然后用户点击back想要返回到A,那么这个时候就不会执行onRestart,而是执行onCreate，重新创建了一个A.

但这个A以前的数据怎么办?是没了吗?
别担心,Activity提供了onSaveInstanceState()回调方法，
这个方法保证Activity被回收之前一定会被调用
```
```kotlin
比如
override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
    /*这个方法携带一个Bundle类型的参数，Bundle提供了一系列方法来保存数据
    比如:putString()保存字符串:有两个参数
    outState:键,用于后面从Bundle中取值
    outPersistentState:真正要保存的内容*/
    super.onSaveInstanceState(outState, outPersistentState)
    var tempData="you typed"
    outState.putString("data_key",tempData)
}

onCreate:
//在Activity被系统回收之前，你通过savedInstanceState()方法保存数据
//savedInstanceState参数会带有之前保存的全部数据,然后用相应的方法取值即可
    if (savedInstanceState!=null){
        val tempData=savedInstanceState.getString("data_key")
        if (tempData != null) {
            Log.d("MainActivity",tempData)
        }
    }
```

# Activity的四种启动模式
## standard
```kotlin
Acitivit的默认启动模式
在这个模式下,每当启动一个新Activity，他会在返回栈中入栈，位于栈顶
standard模式下的Activity，系统不会在意这个Activity是否已经在栈中存在
每次启动都会创建该Activity的新实例，然后入栈
```
**样例**
```kotlin
button1.setOnClickListener { 
    var intent=Intent(this,FirstActivity::class.java)
    //再启动一个FirstActivity
    startActivity(intent)
}

点击button1两次的输出
com.bo.a3_learnactivity.FirstActivity@34de91e
com.bo.a3_learnactivity.FirstActivity@410239f
com.bo.a3_learnactivity.FirstActivity@b161626
每次的Activity的hashCode都不同，说明是三个不同的实例
```
## singleTop
```kotlin
启动Activity的时候,如果发现返回栈顶已经是Activity，
则会直接使用Activity而不会创建新的Activity
```
**样例**
```kotlin
AndroidManfest.xml中FirstActivity的<Activity>标签中加上
android:launchMode="singleTop"

1.
FirstActivity中的onCreate()中
Log.d("FirstActivity",this.toString())
button1.setOnClickListener {
    var intent=Intent(this,FirstActivity::class.java)
    startActivity(intent)
}
点击button1两次输出
com.bo.a3_learnactivity.FirstActivity@34de91e
FirstActivity在栈顶就没有创建新的Activity实例


2.
FirstActivity中的onCreate()中
Log.d("FirstActivity",this.toString())
button1.setOnClickListener {
    var intent=Intent(this,SecondActivity::class.java)
    startActivity(intent)
}
SecondActivity中的onCreate()中
button2.setOnClickListener {
    var intent=Intent(this,FirstActivity::class.java)
    startActivity(intent)
}
点击button2两次输出
com.bo.a3_learnactivity.FirstActivity@34de91e
com.bo.a3_learnactivity.FirstActivity@b161626
com.bo.a3_learnactivity.FirstActivity@70f24ff
从FirstActivity进入到SecondActivity中后，
SecondActivity创建FirstActivity的时候FirstActivity不在栈顶
所以会新的FirstActivity实例
```
## singleTask
```kotlin
这个模式下再创建Activity的时候会先在返回栈中查找有没有这个Activity的实例
如果有就不会创建新的Activity
而是把这个Activity上面的Activity全部出栈，然后使用这个Activity
```
```kotlin
AndroidManfest.xml中FirstActivity的<Activity>标签中加上
android:launchMode="singleTask"

FirstActivity中的onCreate()方法:
Log.d("FirstActivity",this.toString())
button1.setOnClickListener {
    var intent=Intent(this,SecondActivity::class.java)
    startActivity(intent)
}
重写onRestart()():
override fun onRestart() {
    super.onRestart()
    Log.d("FirstActivity","onRestart")
}
SecondActivity中的onCreate()方法:
Log.d("SecondActivity",this.toString())
    button2.setOnClickListener {
    var intent=Intent(this,FirstActivity::class.java)
    startActivity(intent)
}
重写onDestroy():
override fun onDestroy() {
    super.onDestroy()
    Log.d("SecondActivity","onDestroy")
}

点击FirstActivity中的button1，再点击SecondActivity中的button2输出
com.bo.a3_learnactivity.FirstActivity@34de91e
//no activity for token android.os.BinderProxy@58124c0
com.bo.a3_learnactivity.SecondActivity@1b3ccec
onRestart
onDestroy
点击完button2后FirstActivity调用了onRestart而没有调用onCreate(),SecondActivity调用了onDestory()
```
## singleInstance
```kotlin
这个模式的Activity会启用一个新的返回栈来管理这个Activity
(其实singleTask模式中如果指定了不同的tasjAffinity,也会启动一个新的返回栈)
这样能解决什么问题呢？
如果程序中有一个Activity A允许被其他程序调用，那么其他三个启动模式是做不到的
因为每个应用程序都有一个返回栈，A在不同的返回栈都必然会创建新的实例
而使用singleInstance模式下，会有一个单独的返回栈来管理这个Activity，
不管是哪个应用程序都可以来访问这个Activity，都共用一个返回栈
```
**实例**
```kotlin
AndroidManfest.xml中SecondActivity的<Activity>标签中加上
android:launchMode="singleInstance"

FirstActivity的onCreate中
Log.d("FirstActivity","Task id is $taskId")
//打印当前返回栈的id，taskId实际上是调用的父类的getTaskId()方法
button1.setOnClickListener {
    var intent=Intent(this,SecondActivity::class.java)
    startActivity(intent)
}
SecondActivity的onCreate中
Log.d("SecondActivity","Task id is $taskId")
button2.setOnClickListener {
    var intent=Intent(this,ThirdActivity::class.java)
    startActivity(intent)
}
ThirdActivity的onCreate中
Log.d("ThirdActivity","Task id is $taskId")

点击button1后点击button2后点击button3
Task id is 64
//no activity for token android.os.BinderProxy@58124c0
Task id is 65
//no activity for token android.os.BinderProxy@25d988b
Task id is 64
可以看出
FirstActivity的Task id 是64
SecondActivity的Task id 是65
ThirdActivity的Task id 是64
SecondActivity会和FirstActivity、ThirdActivity交互，
所以SecondActivity会被单独放在一个返回栈中，方便使用

然后点击back返回，发现从ThirdActivity返回到了FirstActivity
再点击back返回,发现发现从FirstActivity返回到了SecondActivity
这是为什么呢？
可以从Taskid中看出端倪，ThirdActivity和FirstActivity是在同一个返回栈中，所以点击back后
ThirdActivity出栈，FirstActivity就成为了栈顶元素，自然显示的是FirstActivity
再点击back，FirstActivity出栈后这个返回栈就空了，于是就显示了另一个返回栈的栈顶
```

# Activity的最佳实践(小技巧)
## 知晓当前是在哪一个Activity中
接手一个旧项目时，要能看懂别人的代码，想要知道哪个Activity对应哪个页面的技巧
```kotlin
比如，想要知道
FirstActivity
SecondActivity
ThirdActivity
对应的哪个页面

我们可以先新建一个BaseActivity.kt(不需要注册)
open class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("BaseActivity",javaClass.simpleName)
        //打印当前实例的类名
    }
}

然后让
FirstActivity
SecondActivity
ThirdActivity
继承BaseActivity
class FirstActivity : BaseActivity()
class SecondActivity : BaseActivity()
class ThirdActivity : BaseActivity()

然后运行，点击button1->button2，输出
运行后：D/BaseActivity: FirstActivity对应的页面This a FirstActivity
button1后：D/BaseActivity: SecondActivity对应的页面This a SecondActivity
button2后：D/BaseActivity: ThirdActivity对应的页面This a ThirdActivity
```

## 随时随地退出程序
```kotlin
如果当前在ThirdActivity页面，那么按三次back退出很不方便
所以我们需要一个方法一键退出
```
```kotlin
单例类实现一键退出
object ActivityCollector {
    private val activitis = ArrayList<Activity>()

    fun addActivity(activity: Activity){
        activitis.add(activity)
    }

    fun removeActivity(activity: Activity){
        activitis.remove(activity)
    }

    fun finishAll(){
        for (activity in activitis)
            if(!activity.isFinishing)
                activity.finish()
        activitis.clear()
    }
}

父类
open class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*Log.d("BaseActivity",javaClass.simpleName)*/
        //打印当前实例的类名

        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}
想要点击完button3后所有Activity退出
class ThirdActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_layout)

        button3.setOnClickListener {
            ActivityCollector.finishAll()
            android.os.Process.killProcess(android.os.Process.myPid())//杀死当前程序;myPid()获得当前程序的id参数
        }

        /*Log.d("ThirdActivity","Task id is $taskId")*/
    }
}
```
## 启动Activity的最佳写法
```kotlin
FirstActivity的onCreate()中
/*假如SecondActivity需要用到两个重要的参数,在启动SecondActivity的时候就要用到
那么我们会写*/
 /*button1.setOnClickListener {
    var intent=Intent(this,SecondActivity::class.java)
    intent.putExtra("param1","data1")
    intent.putExtra("param2","data2")
    startActivity(intent)
}*/
/*但是当我们和同事的工作分离是,同事所编写的是SecondActivity，我所编写的是FirstActivity
但是我不知道SecondActivity要什么才能启动，但是如果同事写的SecondActivity中有一个类似静态方法封装了
intent产生和添加信息，我就只需要看看代码，调用这个方法，传参进入就行了
*/
button1.setOnClickListener {
    SecondActivity.actionStart(this,"data1","data2")
}

SecondActivity中
companion object{
    fun actionStart(context: Context, data1:String, data2:String){
        var intent=Intent(context,SecondActivity::class.java)
        intent.putExtra("param1",data1)
        intent.putExtra("param2",data2)
        context.startActivity(intent)
    }
}
```