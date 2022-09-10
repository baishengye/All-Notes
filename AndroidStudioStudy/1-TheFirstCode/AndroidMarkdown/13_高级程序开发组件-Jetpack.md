---
title: Jetpack
author: Bob
top: true
toc: true
mathjax: true
categories: Android学习
tags:
  - 第一行代码
  - Jetpack
abbrlink: 
summary: 《第一行代码》的Jetpack学习
date: 2022-02-12 00:00:00
---


# 高级程序开发组件-Jetpack
优秀的项目开发框架
- MVP
- MVC
- MVVM(Google最为推荐)
Google官方架构组件-jetpack

## jetpack
- jetpack大部分不依赖于任何Android系统版本,而是通常定义在Androidx中
- 拥有好的向下兼容性
**主要有四个部分组成**
- 基础
- 架构
- 行为
- 界面
[![qAoFPS.png](https://s1.ax1x.com/2022/03/19/qAoFPS.png)](https://imgtu.com/i/qAoFPS)

## ViewModel
- ViewModel的一个重要作用就是帮助Activity分担一部分工作，它是专门用于存放与界面相关的数据的，也就是说只要是界面上能看到的数据，他的相关变量都应该存放在ViewModel中，而不是Activity中
- ViewModel还有一个重要的特性----ViewModel和Activity的生命周期不同
  * 当手机横竖屏旋转的时候，Activity会销毁重建，那么数据就很容易丢失，但是我们把数据存在ViewModel的话，即使旋转手机屏幕也不会丢失数据
[![qATXnA.jpg](https://s1.ax1x.com/2022/03/19/qATXnA.jpg)](https://imgtu.com/i/qATXnA)

### ViewModel的基本用法
尝试一个计数器(数字不会因为屏幕反转而归0)
```xml
导包

dependencies {
    ···
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.2.0'
}
```
```xml
布局

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    ···
    android:orientation="vertical">

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/counterBtn"
        android:text="计数器加一"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/infoText"
        android:layout_gravity="center_horizontal"
        android:textSize="32sp"/>

</LinearLayout>
```
```kotlin
ViewModel

class MainViewModel:ViewModel() {
    var counter=0
}
```
```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProviders.of(this).get(MainViewModel::class.java)
        //这个地方一定不能用viewModel=MainViewModel()来创造一个viewModel的实例
        //因为如果这样创建的话ViewModel就是在Activity这个活动的线程中，也就是会和Activity同生共死了
        //当屏幕反转时Activity被销毁时viewModel也会被销毁，Activity创建的时候会创建一个新的viewModel实例
        //而ViewModelProviders.of(this).get(MainViewModel::class.java)获得的viewModel实例是不用依托Activity的
        //这样就把数据保存下来了
        refreshCounter()
        binding.counterBtn.setOnClickListener {
            viewModel.counter++
            refreshCounter()
        }
    }

    private fun refreshCounter(){
        binding.infoText.text=viewModel.counter.toString()//刷新UI上的数据
    }
}
```

### 向ViewModel传递参数
由于我们所有的ViewModel的实例都是通过ViewModelProviders来获取的，所以没有办法向ViewModel的构造函数中传递参数**需要借助ViewModelProvider.Factory来实现**
**使程序退出后再进入也能不清零**
```xml
布局

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    ···>
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/clearBtn"
        android:text="计数器清零"/>
    ···
</LinearLayout>
```
```kotlin
class MainViewModelFactory(private val countReserved:Int):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(countReserved) as T
    }
}
```
```kotlin
class MainViewModel(countReserved:Int):ViewModel() {
    var counter=countReserved//countReserved记录之前的计数值，创建ViewModel的时候把数据恢复
}
```
```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        ···

        //这里通过SharedPreferences储存数据
        sp=getPreferences(Context.MODE_PRIVATE)
        val countReserved=sp.getInt("count_reserved",0)

        viewModel=ViewModelProviders.of(this,MainViewModelFactory(countReserved)).get(MainViewModel::class.java)
        ···
        binding.clearBtn.setOnClickListener {
            viewModel.counter=0
            refreshCounter()
        }
        refreshCounter()
    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved",viewModel.counter)
        }
    }
}
```

## Lifecycles
在编写Adnroid应用的时候，可能会经常遇到需要感知Activity生命周期的情况。
比如:某个界面发出一条网络请求，但是当请求得到响应的时候，界面或许已经关闭了，这个时候就不应该继续对响应的结果进行处理，因此我们需要能够时刻感知到Activity的生命周期，以便在适当的时候进行相应的逻辑控制
```kotlin
class MyObserver:LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart(){//活动开启的时候调用
        Log.d("MyObserver","活动开启")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityStop(){//活动停止的时候调用
        Log.d("MyObserver","活动停止")
    }

    /*@OnLifecycleEvent注解中对应生命周期的事件有6种
    ON_START
    ON_STOP
    ON_PAUSE
    ON_DESTORY
    ON_CREATE
    ON_RESUME
    还有一种 ON_ANY  表示匹配任何生命周期回调*/
}
```
但是这样还不能感知
**这时候需要借助LifecycleOwner**
```kotlin
lifecleOwner.lifecycle.addObserver(MyObserver())
//lifecleOwner得到一个lifecycle生命周期监听器的实例，再用addObserver()把具体需要监听的生命周期控制器传到监听器种，让监听器知道要监听什么
```
**但是lefecleOwner如何得到**
完全没必要自己实现一个，因为我们的Activity是继承自AppCompatActivity的或者Fragment继承自androidx.fragment.app.Fragment的，本身就自带lifecycleOwner
**我们只需要这么写**
```kotlin
class MainActivity : AppCompatActivity() {
    ···
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(MyObserver())
        ···
    }
    ···
}
```

**主动获取生命周期状态**
```kotlin
class MyObserver(val lifecycle: Lifecycle):LifecycleObserver {
···
}
```
MyObserver有了lifecycle对象之后，我们就可以在任何地方使用lifecycle.currentState来主动获取当前的生命周期状态，**lifecycle.currentState返回的生命周期是一个枚举类型**
- INITIALIZED
- DESTROYED
- CREATED
- STARTED
- RESUMED
[![qEAvtK.png](https://s1.ax1x.com/2022/03/19/qEAvtK.png)](https://imgtu.com/i/qEAvtK)

## LiveData
LiveData是jetpack提供的一种响应式编程组件，它可以包含任何类型的数据,并在数据发生变化的时候通知给观察者，LiveData特别适合与ViewData结合在一起使用，至少绝大多数情况下，他是使用在ViewModel种的

### LiveData的基本用法
之前我们所写的计数器是在单线程中运行的，确实可以正确运行，但是如果ViewModel的内部开启了线程去执行一些耗时逻辑，那么点击后就立即去获取最新的数据，得到的肯定还是之前的数据
**我们一直是在Activity手动获取ViewModel的数据，但是ViewModel无法主动提供数据给Activity**
**而且我们一定不能把Activity实例传给ViewModel**
因为如果我们这样做的话，由于ViewModel的生命周期比Activity长，很有可能因为Activity无法释放而造成内存泄漏

**LiveData可以包含任意类型的数据，并把数据发生变化的时候通知给观察者**
我们就可以把计数器的计数使用LiveData包装，然后再Activity中观察，就可以主动把数据变化通知给Activity了
```kotlin
class MainViewModel(countReserved:Int):ViewModel() {
    val counter=MutableLiveData<Int>()
    
    //MutableLiveData是一种可变的LiveData
    /*getValue()获取数据
    setValue()设定数据，只能在主线程中使用
    postValue()在非主线程中设定数据*/

    init {
        counter.value=countReserved//countReserved记录之前的计数值，创建ViewModel的时候把数据恢复
    }

    fun plusOne(){
        val count=counter.value?:0
        counter.value=count+1
    }

    fun clear(){
        counter.value=0
    }
}
```
```kotlin
class MainActivity : AppCompatActivity() {
    ···
    override fun onCreate(savedInstanceState: Bundle?) {
        ···
        binding.counterBtn.setOnClickListener {
            /*viewModel.counter++*/
            viewModel.plusOne()
            refreshCounter()
        }

        binding.clearBtn.setOnClickListener {
            /*viewModel.counter=0*/
            viewModel.clear()
            refreshCounter()
        }

        /*viewModel.counter.observe(this, Observer{count->
            binding.infoText.text=count.toString()
        })*/
        /*此处this是livecycleOwner是一个单抽象方法接口，observe也是单抽象方法接口
        当一个java同时接收两个单抽象方法接口参数的时候，要么都是用函数式API，要么都不使用
        由于这里传入的是一个this，不是函数式使用的，所以Observer也必须不是函数式使用*/
        /*但是这是在kotlin中，Google还是有了一些拓展来使用函数式API的*/
        observe是this订阅这个数据counter，
        viewModel.counter.observe(this){count->
            binding.infoText.text=count.toString()
        }
        refreshCounter()
    }
    ···
}
```

但是这样写是很不规范的，我们暴露了counter这个可变LiveData给外部
比较推荐的是只暴露不可变的LiveData给外部
```kotlin
class MainViewModel(countReserved:Int):ViewModel() {
    val counter: LiveData<Int>
        get() = _counter//外部只能通过get函数通过counter获取_counter，而不能设置直接设置_counter，这样就安全啦

    private val _counter=MutableLiveData<Int>()//此时可变的MutableLiveData是一个外部无法访问的
    init {
        _counter.value=countReserved//countReserved记录之前的计数值，创建ViewModel的时候把数据恢复
    }

    fun plusOne(){
        val count=_counter.value?:0
        _counter.value=count+1
    }

    fun clear(){
        _counter.value=0
    }
}
```

### map和switchMap
但项目变得复杂之后LiveData是不够用的，所以LiveData为了因对这种情况，**提供了两种转换方法**
- map()
- switchMap()

**实例**
有一个自定义的数据类型
```kotlin
data class User(var firstName:String,var lastName:String,var age:Int) {
}
```
```kotlin
class MainViewModel(countReserved:Int):ViewModel() {

    val userLiveData=MutableLiveData<User>()

    ···
}
```
如果这样写的话，要是MainActivity明确只会显示用户的姓名,而完全不关心age的话，就无法做到了，因为ViewModel只能把userLiveData整个暴露出来
**map()可以解决这个问题**
```kotlin
class MainViewModel(countReserved:Int):ViewModel() {

    private val userLiveData=MutableLiveData<User>()
    //私有保证了userLiveData不会暴露出去

    val userName:LiveData<String> = Transformations.map(userLiveData){user->
        "${user.firstName} ${user.lastName}"
    }
    /*map()对LiveData的数据类型进行转换,接收两个参数:
    原始数据对象
    转换函数
    但原始数据userLiveData发生变化的时候,map()函数会监听到数据的变化并且把转换好的数据通知给userName的观察者*/
    ···
}
```
我们之前学的内容都是基于**LiveData对象的实例都是在ViewModel中创建**的，但是不可能一直这么理想,很有可能ViewModel中的某个LiveData对象是调用另外的方法获取的,这是就需要**switchMap()函数**了
```kotlin
object Repository {
    fun getUser(userId:String):LiveData<User>{
        val liveData=MutableLiveData<User>()
        liveData.value=User(userId,userId,0)
        return liveData
    }
}
```
```kotlin
class MainViewModel(countReserved:Int):ViewModel() {
    ···
    fun getUser(userId:String):LiveData<User>{
        return Revisibilitypository.getUser(userId)
    }
    ···
}
```
那么Activity中怎么观察LiveData的数据变化？getUser()返回值是一个LiveData，那么是不是则这么写?
```kotlin
viewModel.getUser(userId).observe(this){this->
    ···
}
```
当然不行,当调用getUser()的时候一个新的LiveData 实例，比如Repository.getUser()返回的LiveData是完整的User(name,age,gender)，但是MainActivity明确只要name，可是直接这样写就把整个User暴露出来了，所以需要**switchMap()来转换一些，只暴露出name**
```kotlin
class MainViewModel(countReserved:Int):ViewModel() {

    ···
    private val userIdLiveData= MutableLiveData<String>()

    switchMap()两个参数:想要观察的数据,转换函数
    val user:LiveData<User> = Transformations.switchMap(userIdLiveData){userId->
        Repository.getUser(userId)
    }

    fun getUser(userId:String){
        userIdLiveData.value=userId
    }
}
```
整个查看的工作流程:
1. 当外部调用mainViewModel中的getUser()方法来获取用户数据的时候，不会发起任何请求或者函数调用，只是把userId设置到了userIdLiveData中
2. 一旦userIdLiveData发生变化，那么观察userIdliveData的switchMap()就会执行，并且调用转换函数 Repository.getUser(userId)获取真正的用户数据
3. 同时把获取的用户数据转换成一个能被观察的LiveData

们调用MainViewModel 的getUser() 方法时传入了一个userId 参数，为了能够观察这个参数的数据变化，又构建了一个userIdLiveData ，然后在switchMap() 方法中再去观察这个LiveData 。但是**ViewModel 中某个获取数据的方法有可能是没有参数的，这个时候代码应该怎么写呢**？
```kotlin
class MainViewModel(countReserved:Int):ViewModel() {

    private val refreshLiveData=MutableLiveData<Any?>()

    val refreshResult=Transformations.switchMap(refreshLiveData){
        Repository.refresh()
    }

    fun refresh(){
        refreshLiveData.value=refreshLiveData.value
    }
}
```
可以看到，这里我们定义了一个不带参数的 refresh() 方法，又对应地定义了一个refreshLiveData ，但是它不需要指定具体包含的数据类型，因此这里我们将LiveData 的泛型指定成 Any? 即可。

接下来就是点睛之笔的地方了，在refresh() 方法中，我们只是将refreshLiveData 原有的数据取出来（默认是空），再重新设置到refreshLiveData 当中，这样就能触发一次数据变化。是的，LiveData 内部不会判断即将设置的数据和原有数据是否相同，只要调用了setValue() 或 postValue() 方法，就一定会触发数据变化事件。

然后我们在Activity 中观察refreshResult 这个LiveData 对象就可以了，这样只要调用了refresh() 方法，观察者的回调中就能够得到最新的数据。

可能你会说，学到现在，只看到了LiveData 和 ViewModel 结合在一起使用，好像和我们上一节学的Lifecycles 组件没什么关系嘛。

其实并不是这样的，LiveData 之所以能够成为Activity 与ViewModel 之间通信的桥梁，并且还不会有内存泄漏的风险，靠的就是Lifecycles 组件。LiveData 的内部使用了 Lifecycles 组件来自我感知生命周期的变化，从而可以在Activity 销毁的时候及时释放引用，避免产生内存泄漏的问题。

另外，由于需要减少性能消耗，当Activity 处于不可见状态的时候（比如手机息屏，或者被其他Activity 遮挡），如果LiveData 中的数据发生了变化，是不会通知给观察者的。只要当Activity 重新恢复可见状态时，才会将数据通知给观察者，而LiveData 之所以能够实现这种细节的优化。依靠的还是Lifecycles 组件。

还有一个小细节，如果Activity 处于不可见庄严的时候，LiveData 发生了多次数据变化，当Activity 恢复可见状态时，只有最新的那份数据才会通知给观察者，前面的数据在这种情况下相当于已经过期了，会被直接丢弃。

## Room

**专门为Android数据库设计的ORM框架**

**ORM对象关系映射**:我们使用的语言是面向对象语言，使用的数据库是关系型数据库，将面向对象语言和关系型数据库之间建立一种映射关系，就是ORM

### 使用Room框架来进行增删改查
**Room整体架构**
- **Entity**:用于定义封装实际数据的实体类,每一个实体类都会在数据库中有一张对应的表，并且表中的列是根据实体类的字段自动生成的
- **Dao**:数据访问对象,通常会在这里对数据库的各项操作进行封装，在实际编程的时候就不用和底层数据库打交道了
- **Database**:用于定义数据库中的关键信息，包括数据库的版本号，包含那些实体类以及提供Dao的访问实例

**导包**
```kotlin
plugins {
    ···
    id 'kotlin-kapt'
}

dependencies {
    ···
    implementation 'androidx.room:room-runtime:2.1.0'
    kapt "androidx.room:room-compiler:2.1.0"
}
```
**Entity**
```kotlin
@Entity
data class User(var firstName:String,var lastName:String,var age:Int) {
    @PrimaryKey(autoGenerate = true)//id是从0开始自增的主键
    var id:Long=0
}
```
**Dao**
```k0tlin
@Dao
interface UserDao {
    @Insert
    fun insertUser(user:User):Long//插入返回主键

    @Update
    fun updateUser(user: User)

    @Query("select * from User")
    fun loadAllUsers():List<User>

    @Query("select * from User where age>:age")
    fun loadUsersOrderThan(age:Int):List<User>

    @Delete
    fun deleteUser(user: User)

    @Query("delete from User where lastName=:lastName")
    //当要通过传入非实体参数增删改数据的时候必须要写SQL语句，而且只能用@Query注解
    fun deleteUserByLastName(lastName:String):Int
}
```
**Database**
```kotlin
@Database(version = 1,entities = [User::class])
//数据库注解(版本号,实体类(可以有多个实体类,用逗号隔开))
abstract class AppDatabase: RoomDatabase(){
//AppDatabase必须是继承自RoomDatabase的抽象类
    abstract fun userDao():UserDao
    //获取userDao的函数也是声明抽象函数，具体实现有Room在底层自动完成

    companion object{
        //伴生类+单例-》静态方法用单例模式获取App的数据库实例,因为原则上全局应该只有一个App的数据库实例
        private var instance:AppDatabase?=null

        @Synchronized
        fun getDatabase(context: Context):AppDatabase{
            //如果AppDatabase实例本来就有就不用再创建了，直接用
            instance?.let {
                return it
            }

            /*否则就要创建一个AppDatabase实例*/
            return Room.databaseBuilder(
                context.applicationContext,//这个参数必须要用applicationContext，不然有内存泄露的风险
                AppDatabase::class.java,//指定创建的AppDatabase的类型
                "app_database"//指定数据库的名称
                ).allowMainThreadQueries()//只有在测试的时候可以用这句代码，允许在主线程中进行查询数据库操作
                .build()
                .apply {
                    instance=this
            }
        }
    }
}
```
**布局**
```kotlin
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    ···>
    ···
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/getDataBtn"
        android:text="查询数据"/>

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/addDataBtn"
        android:text="添加数据"/>

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/updateData"
        android:text="更新数据"/>

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/deleteData"
        android:text="删除数据"/>
</LinearLayout>
```
```kotlin
class MainActivity : AppCompatActivity() {
    ···
    override fun onCreate(savedInstanceState: Bundle?) {
        ···
        var userDao=AppDatabase.getDatabase(this).userDao()
        val user1=User("章","北海",49)
        val user2=User("罗","辑",20)
        //数据库操作都是很耗时的，Room是不允许数据库操作出现在主线程中的，所以要把操作放在子线程中
        binding.addDataBtn.setOnClickListener {
            thread {
                user1.id=userDao.insertUser(user1)
                user2.id=userDao.insertUser(user2)
            }
        }

        binding.updateData.setOnClickListener {
            thread {
                user1.age=42
                userDao.updateUser(user1)
            }
        }

        binding.deleteData.setOnClickListener {
            thread {
                userDao.deleteUserByLastName("辑")
            }
        }

        binding.getDataBtn.setOnClickListener {
            thread {
                for (user in userDao.loadAllUsers()){
                    Log.d("MainActivity",user.toString())
                }
            }
        }
    }
}
```

### Room框架下的数据库升级
**粗暴方式**
只用于测试阶段,把数据库摧毁重建,超级不建议使用
```kotlin
Room.databaseBuilder(
    context.applicationContext,
    AppDatabase::class.java,
    "app_database")
    .fallbackToDestructiveMigration()//摧毁重建
    .build()
```

#### 正经方式
**比如数据库中需要加一张Book表**
```kotlin
@Entity
data class Book(var name:String,var pages:Int) {
    @PrimaryKey(autoGenerate = true)
    var id:Long=0
}
```
```kotlin
@Dao
interface BookDao {
    @Insert
    fun insertBook(book:Book):Long

    @Query("select * from Book")
    fun loadAllBook():List<Book>
}
```
```kotlin
@Database(version = 2,entities = [User::class])
//数据库注解(版本号,实体类(可以有多个实体类,用逗号隔开))
abstract class AppDatabase: RoomDatabase(){
//AppDatabase必须是继承自RoomDatabase的抽象类
    abstract fun userDao():UserDao
    //获取userDao的函数也是声明抽象函数，具体实现有Room在底层自动完成

    abstract fun bookDao():BookDao

    companion object{

        private val MIGRATION_1_2= object : Migration(1,2) {
            //Migration(1,2)表示从数据库版本1升级到2就执行这个匿名类将数据库升级
            //MIGRATION_1_2命名比较讲究表示从1升级到2
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("" +//还是要用SQL语句来操作
                        "create table Book (" +
                            "id integer primary key autoincrement not null," +
                            "name text not null," +
                            "pages integer not null)")
            }
        }
        //伴生类+单例-》静态方法用单例模式获取App的数据库实例,因为原则上全局应该只有一个App的数据库实例
        private var instance:AppDatabase?=null

        @Synchronized
        fun getDatabase(context: Context):AppDatabase{
            //如果AppDatabase实例本来就有就不用再创建了，直接用
            instance?.let {
                return it
            }

            /*否则就要创建一个AppDatabase实例*/
            return Room.databaseBuilder(
                context.applicationContext,//这个参数必须要用applicationContext，不然有内存泄露的风险
                AppDatabase::class.java,//指定创建的AppDatabase的类型
                "app_database"//指定数据库的名称
                )//.allowMainThreadQueries()//只有在测试的时候可以用这句代码，允许在主线程中进行查询数据库操作
                //.fallbackToDestructiveMigration()//摧毁重建
                .addMigrations(MIGRATION_1_2)
                .build()
                .apply {
                    instance=this
            }
        }
    }
}
```

**如果只在一个表中增加一个字段**
```kotlin
@Entity
data class Book(var name:String,var pages:Int,var author:String) {
    @PrimaryKey(autoGenerate = true)
    var id:Long=0
}
```
```kotlin
@Database(version = 2,entities = [User::class,Book::class])
//数据库注解(版本号,实体类(可以有多个实体类,用逗号隔开))
abstract class AppDatabase: RoomDatabase(){
//AppDatabase必须是继承自RoomDatabase的抽象类
    abstract fun userDao():UserDao
    //获取userDao的函数也是声明抽象函数，具体实现有Room在底层自动完成

    abstract fun bookDao():BookDao

    companion object{

        private val MIGRATION_1_2= object : Migration(1,2) {
            //Migration(1,2)表示从数据库版本1升级到2就执行这个匿名类将数据库升级
            //MIGRATION_1_2命名比较讲究表示从1升级到2
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("" +//还是要用SQL语句来操作
                        "create table Book (" +
                            "id integer primary key autoincrement not null," +
                            "name text not null," +
                            "pages integer not null)")
            }
        }

        private val MIGRATION_2_3=object :Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table Book add column author text not null default 'Unknown'")
            }
        }
        //伴生类+单例-》静态方法用单例模式获取App的数据库实例,因为原则上全局应该只有一个App的数据库实例
        private var instance:AppDatabase?=null

        @Synchronized
        fun getDatabase(context: Context):AppDatabase{
            //如果AppDatabase实例本来就有就不用再创建了，直接用
            instance?.let {
                return it
            }

            /*否则就要创建一个AppDatabase实例*/
            return Room.databaseBuilder(
                context.applicationContext,//这个参数必须要用applicationContext，不然有内存泄露的风险
                AppDatabase::class.java,//指定创建的AppDatabase的类型
                "app_database"//指定数据库的名称
                )//.allowMainThreadQueries()//只有在测试的时候可以用这句代码，允许在主线程中进行查询数据库操作
                //.fallbackToDestructiveMigration()//摧毁重建
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
                .apply {
                    instance=this
            }
        }
    }
}
```

## WorkManager
由于频繁的功能和API变更，开发者都很不开心，所以 **Google推出了WorkManager组件来保证应用程序在不同系统版本上的兼容性**
- 他适合用于处理一些要求定时执行的任务
- 他可以根据操作系统的版本自动选择底层时使用**AlarmManager还是JobScheduler**,从而降低使用成本
- 它还支持周期性任务
- 链式任务处理

**WorkManager和Service并不相同,也没有直接联系**
- Service是Android系统的四大组件之一，他没销毁的时候一直在后台运行
- WorkManager只是一个处理定时任务的工具，它可以保证即使在应用退出甚至手机重启的情况下，之前注册的任务依然可以执行，因此WorkManager很适合用于执行定期和服务器进行交互的任务，比如周期性同步数据等等
  
**WorkManager注册的周期性任务不能保证一定会准时执行**
这并非是bug，系统为了减少电量消耗，可能会将触发事件临近的几个任务在一起执行，这样可以**大幅度减少CPU被唤醒的次数，从而延长电池的使用时间**

### WorkManager的基本用法
**导包**
```xml
dependencies {
    ···
    implementation 'androidx.work:work-runtime:2.2.0'
}
```
**WorkManager基本用法有3步**
1. 定义一个后台任务，并且实现具体逻辑
2. 配置该后台任务的运行条件和约束信息，并构建后台任务请求
3. 将该后台任务请求传入WorkManager的enquequ()方法中，系统会在合适的时间运行

**定义后台任务**
```kotlin
class SimpleWorker(context: Context,params:WorkerParameters):Worker(context,params) {
    override fun doWork(): Result {//doWork不会在主线程中运行
        Log.d("SimpleWorker" ,"SimpleWorker开始工作")
        return Result.success()//返回逻辑执行的结果
    }
}
```

**配置任务运行条件和约束信息**
```kotlin
val request=OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
//根据后台任务对应的class进行构建后台任务实例

val request=PeriodicWorkRequest.Builder(SimpleWorker::class.java,15,TimeUnit.MINUTES).build()
```
WorkRequest.Builder
- 子类:OnTimeWorkRequest.Builder:用于构建单词运行的后台任务
- 子类:PeriodicWorkRequest.Builder:构建周期性的后台任务请求,周期不能少于15分钟

**把构建出的后台任务请求传入WorkManager的enqueue()方法中,系统就会再合适的时机运行了
```kotlin
WorkManager.getInstance(this).enqueue(request)//enqueue:这个任务实际是在子线程中运行的
```

**示例**
**布局**
```xml
<Button
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:id="@+id/doWorkBtn"
    android:text="开启后台任务"/>
```
**Activity**
```kotlin
binding.doWorkBtn.setOnClickListener {
    val request=OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
    WorkManager.getInstance(this).enqueue(request)//enqueue:这个任务实际是在子线程中运行的
}
```

### 使用WorkManager处理复杂的任务
其实WorkManager是允许**控制运行时间和其他许多方面**
```kotlin
val request=OneTimeWorkRequest.Builder(SimpleWorker::class.java)
                .setInitialDelay(5,TimeUnit.MINUTES)//延迟5分钟
                .addTag("simple")//添加一个标签(我们可以通过标签来取消后台任务)
                .setBackoffCriteria(BackoffPolicy.LINEAR,10,TimeUnit.MINUTES)//如果任务失败就重启任务Result.retry()
                    //指定三个参数
                    //第一个参数是用于指定如果任务再次执行失败，下次的时间以什么方式延迟
                            // LINEAR:下次重试的时间以线性的方式延迟
                            //EXPONENTIAL:下次重试的时间以指数的方式延迟
                    //第二个参数和第三个参数指延迟的时间:10分钟
                .build()
            WorkManager.getInstance(this).enqueue(request)//enqueue:这个任务实际是在子线程中运行的
            WorkManager.getInstance(this).cancelAllWorkByTag("simple")//标签来取消后台任务
            WorkManager.getInstance(this).cancelWorkById(request.id)//根据id来取消后台任务
            WorkManager.getInstance(this).cancelAllWork()//取消所有后台任务
            WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(request.id)//根据id来监听,还可以根据标签监听(getWorkInfoByTagLiveData)
                .observe(this){workInfo->//监听后台任务的执行结果
                    if(workInfo.state==WorkInfo.State.SUCCEEDED){
                        Log.d("MainActivity","任务执行成功")
                    }else{
                        Log.d("MainActivity","任务执行失败")
                    }
                }

```

### WorkManager链式任务
假如这里定义了三个后台任务:同步数据，压缩数据,上传数据
```kotlin
val sync = ...
val compress=...
val upload=...
WorkManager.getInstance(this)
    .beginWith(sync)
    .then(compress)
    .then(upload)
    .enqueue()
```