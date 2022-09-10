---
title: 高级技巧
author: Bob
top: true
toc: true
mathjax: true
categories: Android学习
tags:
  - 第一行代码
  - 高级技巧
abbrlink: 
summary: 《第一行代码》的高级技巧学习
date: 2022-02-12 00:00:00
---

# 天气预报App

## 功能需求和技术可行性分析

### 功能需求
- 可以搜索全球大多数国家的各城市数据
- 可以查看全球绝大多数城市的天气信息
- 可以自由切换城市，查看其它城市的天气
- 可以手动刷新实时的天气
- 可以对标一个优秀的天气APP-----彩云天气

### 技术可行性分析
先不谈我自己有多拉跨
- 彩云天气的免费API接口可以查看全球100多个国家的城市数据
- API每天最多会提供1万次的免费请求

### 获取彩云天气开放API


## git项目到github


## 搭建MVVM项目架构
- **M:** Model是数据模型部分
- **V:** View是界面展示部分
- **VM:** 连接数据模型和界面展示的桥梁，从而实现业务逻辑和界面展示分离的程序结构设计
- 一个优秀的项目结构除了这三部分，还有仓库，数据源等等

[![qnAn5n.png](https://s1.ax1x.com/2022/03/21/qnAn5n.png)](https://imgtu.com/i/qnAn5n)
- **Ui控制层**包含了我们平时写的Activity，Fragment，布局文件等等
- **ViewModel层**用于持有和UI元素有关的数据，以保证这些数据在屏幕选装的时候不会丢失，并且还要提供接口给UI控制层调用，以及和仓库层通信
- **仓库层**主要是判断调用方请求的数据应该是从本地数据源获取还是从网络数据源获取，并将获取到的数据返回到调用方
- **本地数据源**可以使用数据库，文件，SharedPerferences实现
- **网络数据源**则通常是Retrofit访问服务器提供的WebService接口实现
- 所有箭头都是单向的，表示上层持有下层的引用，并且引用不能跨层，就是第一层不会持有第三层的引用

### 准备工作
[![qnVrAH.png](https://s1.ax1x.com/2022/03/21/qnVrAH.png)](https://imgtu.com/i/qnVrAH)
- **logic**: 业务逻辑相关代码
  - **dao**：存放数据访问对象相关代码
  - **model**: 对象模型相关代码
  - **network**: 网络相关代码
- **ui**：界面展示相关代码
  - **place**：天气中的位置主界面
  - **weather**: 天气中的天气主界面

**导包**
```kotlin
dependencies {
    ···
    implementation 'androidx.recyclerview:recyclerview:1.2.0'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.3.1'
    implementation 'com.google.android.material:material:1.3.0'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.6.1'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0-native-mt'
}
```

## 搜索全球城市数据

### 实现逻辑层代码

**由于MVVM架构的设计，ViewModel层开始就不再持有Activity的引用，所以我们需要全局获取context**
```kotlin
class BoWeatherApplication:Application() {
    companion object{
        const val TOKEN=""//唯一的彩云天气的令牌，获取数据用
        //伴生匿名单例类，也就是说一个application实例中只会有一个这个类，就让整个APP只会有一个全局Context
        @SuppressLint("StaticFieldLeak")//把context注解成静态的
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext//设置Context
    }
}
```
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.bo.boweather.android">
    <!--android:name=".BoWeatherApplication这个是告知程序启动的时候要初始化BoWeatherApplication类,从而获取全局Context-->
    <application
        android:name=".BoWeatherApplication"
        ···>
        ···
    </application>
</manifest>
```

#### 按照MVVM项目结构图自底向上实现

**定义位置数据模型**
```kotlin
data class PlaceResponse(val status:String,val places:List<Place>) {
    /***
     * status:请求的状态
     * places:位置
     */
}

data class Place(val name: String, val location:Location,
                 @SerializedName("formatted_address") val address:String){
    /***
     * name:位置名称
     * location:位置的经纬度
     * @SerializedName("formatted_address") address:位置的地址,
     * 可能请求返回的字段json命名和kotlin命名规范不一样，用注解的方式给json字段和kotlin字段建立一个映射关系
     */

}

data class Location(val lng:String,val lat:String){
    /***
     * lng:位置的经度
     * lat:位置的纬度
     */
}
```

**网络层**

**访问彩云天气城市搜索API的Retrofit接口**
```kotlin
interface PlaceService {
    @GET("v2/place?token=${BoWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
    /***
     * 当有类实现接口调用searchPlaces的时候Retrofit会自动发起一条请求，去访问@GET中的路径
     * 搜索的参数中只有query是动态指定的，有@Query注解自动构建到@GET的路径中
     * Call<PlaceResponse>就会把服务器返回的json数据解析成PlaceResponse
     */
}
```

**为了使用接口，还需要创建一个Retrofit构建器**
```kotlin
object ServiceCreator {
    private const val BASE_URL="https://api.caiyunapp.com/"

    private val retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)//根路径
        .addConverterFactory(GsonConverterFactory.create())//指定解析时的转换库(gson解析json字符串)
        .build()

    fun<T> create(serviceClass: Class<T>):T= retrofit.create(serviceClass)//创建接口的动态代理对象

    inline fun <reified T> create():T= create(T::class.java)//内联函数+泛型实化创建接口的动态代理对象
}
```

**定义一个同一的网络数据源访问入口，对所有网络请求的API进行封装**
```kotlin
object BoWeatherNetwork {
    private val placeService=ServiceCreator.create(PlaceService::class.java)
    //利用ServiceCreator这个单例类(看作静态)创建出一个placeService的动态代理对象

    suspend fun searchPlaces(query:String)= placeService.searchPlaces(query).await()
    //发起搜索城市的请求，searchPlaces是一个挂起函数
    //暂停函数是可以控制启动，暂停和恢复函数。
    // 关于挂起函数要记住的最重要的一点是，仅允许从协程或另一个挂起函数。
    //这个函数的流程就是:
    //发起网络请求之后await就会把当前的协程阻塞，searchPlaces就会挂起，当await中获取到数据之后就会恢复当前协程的执行


    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine { continuation ->
            enqueue(object :Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body=response.body()
                    if(body!=null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}
```

**没有进行本地缓存，直接每次都网络请求获取最新的城市数据，仓库层的同一封装入口**
```kotlin
object Repository {
    /*liveData()函数是lifecycle-livedata-ktx库中提供的一个功能,他能自动构建一并返回一个liveData对象,
    然后在他的代码块中提供一个挂起函数的上下文，这样我们就可以在liveData()函数中调用任意的挂起函数*/
    fun searchPlaces(query:String)= liveData(Dispatchers.IO) {
        //由于网络请求是无法在主线程中运行的，所以我们用Dispatchers.IO把liveData代码块中的逻辑求换到子线程中运行
        val result=try {
            val placeResponse=BoWeatherNetwork.searchPlaces(query)//发起请求接收数据
            if(placeResponse.status=="ok"){//如果请求的结果是成功的话
                val places=placeResponse.places///就把请求的结果数据保存下来
                Result.success(places)//把成功获取的城市数据列表包装起来
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))//如果没成功就把异常信息包装起来
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)//如果没成功就把异常信息包装起来
        }
        emit(result)//类似调用LiveData.setValue()，通知数据变化相当于把result设置到livedata中
    }
}
```

**逻辑层的最后一步-----ViewModel层**
- ViewModel相当于逻辑层和UI层之间的一个桥梁
- ViewModel通常和Activity或Fragment一一对应
  
```kotlin
class PlaceViewModel:ViewModel() {
    private val searchLiveData=MutableLiveData<String>()//存放的字符串是城市的名称

    val placeList=ArrayList<Place>()//用于缓存在界面上显示的城市信息

    val placeLiveData=Transformations.switchMap(searchLiveData){query->
        Repository.searchPlaces(query)//根据城市的名称来查询
    }

    fun searchPlaces(query:String){
        searchLiveData.value=query
    }
}
```

### UI层

**搜索城市数据的功能要复用，所以写在Fragmnet中，要复用的时候就直接在布局中引入Fragment**
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="?android:windowBackground">

    <ImageView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/bgImageView"
        android:layout_alignParentBottom="true"
        android:src="@drawable/bg_place"/>

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:id="@+id/actionBarLayout"
        android:background="@color/purple_200">

        <EditText
            android:layout_width="match_parent"
            android:layout_height="40dp"
            android:id="@+id/searchPlaceEdit"
            android:layout_gravity="center_vertical"
            android:layout_marginStart="10dp"
            android:layout_marginEnd="10dp"
            android:paddingStart="10dp"
            android:paddingEnd="10dp"
            android:hint="输入地址"
            android:background="@drawable/search_bg"/>
    </FrameLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/recyclerView"
        android:layout_below="@+id/actionBarLayout"
        android:visibility="gone"/>
</RelativeLayout>
```

**列表用的是recyclerview，所以需要他的子项布局,用的是卡片式布局**
```xml
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="130dp"
    android:layout_margin="12dp"
    app:cardCornerRadius="4dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:layout_gravity="center_vertical"
        android:layout_margin="18dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/placeName"
            android:textColor="?android:attr/textColorPrimary"
            android:textSize="20sp"/>
        <!--用于显示搜索到的地区名-->

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/placeAddress"
            android:layout_marginTop="10dp"
            android:textColor="?android:attr/textColorSecondary"
            android:textSize="14sp"/>
        <!--显示该地区的详细地址-->
    </LinearLayout>

</com.google.android.material.card.MaterialCardView>
```

**recyclerview是需要适配器的**
```kotlin
class PlaceAdapter(private val fragment: Fragment, private val placeList: List<Place>):
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val placeName:TextView=view.findViewById(R.id.placeName)
        val placeAddress:TextView=view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place=placeList[position]
        holder.placeAddress.text=place.address
        holder.placeName.text=place.name
    }

    override fun getItemCount(): Int {
        return placeList.size
    }
}
```

**实现Fragment**
```kotlin
class PlaceFragment : Fragment() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if(content.isNotEmpty()) {//当搜索框中的内容发生变化的时候就获取新的内容
                viewModel.searchPlaces(content)
            }else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(this, Observer{ result ->
            //监控ViewData中的数据变化,当数据有变化就回调到observe中
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)//把地址添加到placeList中后适配器就会感应到，然后把数据添加到UI中
                adapter.notifyDataSetChanged()//刷新UI
            }else{
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_LONG).show()
                result.exceptionOrNull()?.printStackTrace()//数据为空就toast一个提示
            }
        })
    }

}
```

**Fragment是不能直接显示在界面上的，需要添加到Activity中才行**
```xml
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <fragment
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/placeFragment"
        android:name="com.bo.boweather.android.ui.place.PlaceFragment"/>

</FrameLayout>
```

**PlaceFragment中已经定义了搜索框，那么标题栏就不要**
```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
    <style name="Theme.随波天气" parent="Theme.MaterialComponents.DayNight.NoActionBar">
        ···
    </style>
</resources>
```

**添加网络权限**
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.bo.boweather.android">
    <uses-permission android:name="android.permission.INTERNET"/>
    ···
</manifest>
```

## 显示天气信息

### 逻辑层实现

**彩云天气返回的实时天气数据**
```json
{
    "status": "ok",
    "api_version": "v2.5",
    "api_status": "active",
    "lang": "zh_CN",
    "unit": "metric",
    "tzshift": 28800,
    "timezone": "Asia\/Taipei",
    "server_time": 1647911032,
    "location": [
        25.1552,
        121.6544
    ],
    "result": {
        "realtime": {
            "status": "ok",
            "temperature": 12.2,
            "humidity": 0.86,
            "cloudrate": 1.0,
            "skycon": "CLOUDY",
            "visibility": 1.38,
            "dswrf": 59.2,
            "wind": {
                "speed": 18.58,
                "direction": 17.04
            },
            "pressure": 100107.16,
            "apparent_temperature": 9.1,
            "precipitation": {
                "local": {
                    "status": "ok",
                    "datasource": "gfs",
                    "intensity": 0.0
                }
            },
            "air_quality": {
                "pm25": 4,
                "pm10": 0,
                "o3": 0,
                "so2": 0,
                "no2": 0,
                "co": 0,
                "aqi": {
                    "chn": 8,
                    "usa": 0
                },
                "description": {
                    "usa": "",
                    "chn": "\u4f18"
                }
            },
            "life_index": {
                "ultraviolet": {
                    "index": 2.0,
                    "desc": "\u5f88\u5f31"
                },
                "comfort": {
                    "index": 9,
                    "desc": "\u5bd2\u51b7"
                }
            }
        },
        "primary": 0
    }
}
```

**彩云天气返回的实时天气数据很多，所以我们需要选取一些重要的数据**
**形如**
```json
{
    "status": "ok",
    "result": {
        "realtime":{
            "temperature": 23.16
            "skycon":"WIND"
            "air_quality": {
                "aqi":{"chn": 17.0}
            }
        }
    }
}
```

**需要按照这种json格式来信已数据模型**
```kotlin
data class RealtimeResponse(val status:String,val result:Result) {
    /*这里把所有的RealtimeResponse中用到的数据模型类都定义在RealtimeResponse里面
    可以避免和其他接口中的数据模型类同名冲突*/
    data class Result(val realtime:Realtime)

    data class Realtime(val skycon:String,val temperature:Float,
                        @SerializedName("air_quality") val airQuality:AirQuality)

    data class AirQuality(val aqi:Aqi)

    data class Aqi(val chn:Float)
}
```

**天气预报数太多了，就不展示了**

**获取未来几天的天气信息返回的json数据，简化后**
```json
{
    "status": "ok",
    "result": {
        "daily":{
            "temperature": [{"max":25.7,"min":20.3},...],
            "skycon":[{"value":"CLOUDY","date":"2019-10-20T00:00+08:00"},...],
            "life_index": {
                "coldRisk":[{"desc":"易发"},...],
                "carWashing":[{"desc":"适宜"},...],
                "ultraviolet":[{"desc":"无"},...],
                "dressing":[{"desc":"舒适"},...]
            }
        }
    }
}
```

**这段json数据返回的数据都是数组，数组中的每个元素对应一天的数据,所以在数据模型中，我们可以使用List数组来对应json中的数组元素映射**
```kotlin
class DailyResponse(val status:String,val result: Result) {
    data class Result(val daily:Daily)

    data class Daily(val temperature:List<Temperature>,
                      val skycon:List<Skycon>,
                      @SerializedName("life_index") val lifeIndex:LifeIndex)

    data class Temperature(val max:Float,val min:Float)

    data class Skycon(val value:String,val date:Date)

    data class LifeIndex(val coldRisk:List<LifeDescription>,
                         val carWashing:List<LifeDescription>,
                         val ultraviolet:List<LifeDescription>,
                         val dressing:List<LifeDescription>)
    
    data class LifeDescription(val desc:String)
}
```

**还需要一个Weather类把Realtime和Daily封装起来**
```kotlin
data class Weather(val realtime: RealtimeResponse.Realtime,val daily: DailyResponse.Daily) {
}
```

### 开始网络层

**定义一个访问天气API的Retrofit接口**
```kotlin
//通过经纬度来获取地区的信息
interface WeatherService {
    @GET("v2.5/${BoWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng:String,@Path("lat") lat:String):Call<RealtimeResponse>
    //根据@Path注解把经纬度参数填充到路径里面，请求返回的数据是一个json文件，然后回调Call并且gson解析到对应的数据模型里面

    @GET("v2.5/${BoWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng:String,@Path("lat") lat:String):Call<DailyResponse>
}
```

**在网络数据源访问入口对新增的WeatherService接口封装**

```kotlin
object BoWeatherNetwork {
    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()

    private val placeService=ServiceCreator.create(PlaceService::class.java)
    //利用ServiceCreator这个单例类(看作静态)创建出一个placeService的动态代理对象

    suspend fun searchPlaces(query:String)= placeService.searchPlaces(query).await()
    //发起搜索城市的请求，searchPlaces是一个挂起函数
    //暂停函数是可以控制启动，暂停和恢复函数。
    // 关于挂起函数要记住的最重要的一点是，仅允许从协程或另一个挂起函数。
    //这个函数的流程就是:
    //发起网络请求之后await就会把当前的协程阻塞，searchPlaces就会挂起，当await中获取到数据之后就会恢复当前协程的执行


    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine { continuation ->
            enqueue(object :Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body=response.body()
                    if(body!=null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}
```

**网络层完了就要去仓库层做事了**
```kotlin
object Repository {
    ···
    fun refreshWeather(lng:String,lat:String)= liveData(Dispatchers.IO) {
        val result=try {
            coroutineScope {
                val deferredRealtime=async {
                    /*在async函数中发起请求，然后调用他的await()函数，就会有一个效果
                    只有网络请求发出后这个协程就会挂起，直到获取到结果后协程才会启动*/
                    BoWeatherNetwork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily=async {
                    BoWeatherNetwork.getDailyWeather(lng, lat)
                }
                val realtimeResponse=deferredRealtime.await()
                val dailyResponse=deferredDaily.await()
                if(realtimeResponse.status=="ok"&&dailyResponse.status=="ok"){
                    val weather=Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(
                        RuntimeException(
                            "realtimeResponse status is ${realtimeResponse.status}\n"+
                            "dailyResponse is ${dailyResponse.status}"))
                }
            }
        }catch (e:Exception){
            Result.failure<Weather>(e)
        }
        emit(result)
    }
}
```

**其实这个网络数据源访问接口中的每个网络请求接口都可能抛出异常,所以我们可以把封装一个同一的入口函数，使只需要一次try{}catch{}就行了**
```kotlin
object Repository {
    /*liveData()函数是lifecycle-livedata-ktx库中提供的一个功能,他能自动构建一并返回一个liveData对象,
    然后在他的代码块中提供一个挂起函数的上下文，这样我们就可以在liveData()函数中调用任意的挂起函数*/
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        //由于网络请求是无法在主线程中运行的，所以我们用Dispatchers.IO把liveData代码块中的逻辑求换到子线程中运行
        val placeResponse = BoWeatherNetwork.searchPlaces(query)//发起请求接收数据
        if (placeResponse.status == "ok") {//如果请求的结果是成功的话
            val places = placeResponse.places///就把请求的结果数据保存下来
            Result.success(places)//把成功获取的城市数据列表包装起来
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))//如果没成功就把异常信息包装起来
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                /*在async函数中发起请求，然后调用他的await()函数，就会有一个效果
                   只有网络请求发出后这个协程就会挂起，直到获取到结果后协程才会启动*/
                BoWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                BoWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtimeResponse status is ${realtimeResponse.status}\n" + "dailyResponse is ${dailyResponse.status}"
                    )
                )
            }
        }
    }


    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
    //值得注意的是liveData()函数中是由挂起函数的上下文的，但是一旦到lambda代码块中就没有挂起函数的上下文，也就是说
        //封装到函数中之后就不能使用await()了，所以我们需要把block定义成一个挂起函数
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}
```

**ViewModel层**
```kotlin
class WeatherViewModel:ViewModel() {
    private val localLiveData=MutableLiveData<Location>()

    var locationLng=""
    var locationlat=""
    var placeName=""
    
    val weatherLiveData=Transformations.switchMap(localLiveData){ location->
        Repository.refreshWeather(location.lng,location.lat)
    }
    
    fun refresh(lng:String,lat:String){
        localLiveData.value= Location(lng, lat)
    }
} 
```                  

### UI层代码

**now.xml作为当前天气信息的布局**
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/nowLayout"
    android:layout_width="match_parent"
    android:layout_height="530dp"
    android:orientation="vertical">

    <FrameLayout
        android:id="@+id/titleLayout"
        android:layout_width="match_parent"
        android:layout_height="70dp"
        android:fitsSystemWindows="true">

        <Button
            android:id="@+id/navBtn"
            android:layout_width="30dp"
            android:layout_height="30dp"
            android:layout_marginStart="15dp"
            android:layout_gravity="center_vertical"
            android:background="@drawable/ic_home" />

        <TextView
            android:id="@+id/placeName"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="60dp"
            android:layout_marginEnd="60dp"
            android:layout_gravity="center"
            android:singleLine="true"
            android:ellipsize="middle"
            android:textColor="#fff"
            android:textSize="22sp" />

    </FrameLayout>

    <LinearLayout
        android:id="@+id/bodyLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:orientation="vertical">

        <TextView
            android:id="@+id/currentTemp"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:textColor="#fff"
            android:textSize="70sp" />

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:layout_marginTop="20dp">

            <TextView
                android:id="@+id/currentSky"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textColor="#fff"
                android:textSize="18sp" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="13dp"
                android:textColor="#fff"
                android:textSize="18sp"
                android:text="|" />

            <TextView
                android:id="@+id/currentAQI"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="13dp"
                android:textColor="#fff"
                android:textSize="18sp" />

        </LinearLayout>

    </LinearLayout>

</RelativeLayout>
```

**forecast.xml未来几天的天气信息布局**

```xml
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginLeft="15dp"
    android:layout_marginRight="15dp"
    android:layout_marginTop="15dp"
    app:cardCornerRadius="4dp">

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="15dp"
            android:layout_marginTop="20dp"
            android:layout_marginBottom="20dp"
            android:text="预报"
            android:textColor="?android:attr/textColorPrimary"
            android:textSize="20sp"/>

        <LinearLayout
            android:id="@+id/forecastLayout"
            android:orientation="vertical"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
        </LinearLayout>

    </LinearLayout>

</com.google.android.material.card.MaterialCardView>
```

**forecast_item.xml未来天气的子布局**

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="15dp">

    <TextView
        android:id="@+id/dateInfo"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:layout_weight="4" />

    <ImageView
        android:id="@+id/skyIcon"
        android:layout_width="20dp"
        android:layout_height="20dp" />

    <TextView
        android:id="@+id/skyInfo"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:layout_weight="3"
        android:gravity="center" />

    <TextView
        android:id="@+id/temperatureInfo"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:layout_weight="3"
        android:gravity="end" />

</LinearLayout>
```

**life_index.xml生活指数的布局**

```xml
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="15dp"
    app:cardCornerRadius="4dp">

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="15dp"
            android:layout_marginTop="20dp"
            android:text="生活指数"
            android:textColor="?android:attr/textColorPrimary"
            android:textSize="20sp"/>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp">

            <RelativeLayout
                android:layout_width="0dp"
                android:layout_height="60dp"
                android:layout_weight="1">

                <ImageView
                    android:id="@+id/coldRiskImg"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerVertical="true"
                    android:layout_marginStart="20dp"
                    android:src="@drawable/ic_coldrisk" />

                <LinearLayout
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerVertical="true"
                    android:layout_toEndOf="@id/coldRiskImg"
                    android:layout_marginStart="20dp"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:textSize="12sp"
                        android:text="感冒" />

                    <TextView
                        android:id="@+id/coldRiskText"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="4dp"
                        android:textSize="16sp"
                        android:textColor="?android:attr/textColorPrimary" />
                </LinearLayout>

            </RelativeLayout>

            <RelativeLayout
                android:layout_width="0dp"
                android:layout_height="60dp"
                android:layout_weight="1">

                <ImageView
                    android:id="@+id/dressingImg"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerVertical="true"
                    android:layout_marginStart="20dp"
                    android:src="@drawable/ic_dressing" />

                <LinearLayout
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerVertical="true"
                    android:layout_toEndOf="@id/dressingImg"
                    android:layout_marginStart="20dp"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:textSize="12sp"
                        android:text="穿衣" />

                    <TextView
                        android:id="@+id/dressingText"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="4dp"
                        android:textSize="16sp"
                        android:textColor="?android:attr/textColorPrimary" />
                </LinearLayout>

            </RelativeLayout>

        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="20dp">

            <RelativeLayout
                android:layout_width="0dp"
                android:layout_height="60dp"
                android:layout_weight="1">

                <ImageView
                    android:id="@+id/ultravioletImg"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerVertical="true"
                    android:layout_marginStart="20dp"
                    android:src="@drawable/ic_ultraviolet" />

                <LinearLayout
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerInParent="true"
                    android:layout_toEndOf="@id/ultravioletImg"
                    android:layout_marginStart="20dp"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:textSize="12sp"
                        android:text="实时紫外线" />

                    <TextView
                        android:id="@+id/ultravioletText"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="4dp"
                        android:textSize="16sp"
                        android:textColor="?android:attr/textColorPrimary" />
                </LinearLayout>

            </RelativeLayout>

            <RelativeLayout
                android:layout_width="0dp"
                android:layout_height="60dp"
                android:layout_weight="1">

                <ImageView
                    android:id="@+id/carWashingImg"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerVertical="true"
                    android:layout_marginStart="20dp"
                    android:src="@drawable/ic_carwashing" />

                <LinearLayout
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerInParent="true"
                    android:layout_toEndOf="@id/carWashingImg"
                    android:layout_marginStart="20dp"
                    android:orientation="vertical">

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:textSize="12sp"
                        android:text="洗车" />

                    <TextView
                        android:id="@+id/carWashingText"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="4dp"
                        android:textSize="16sp"
                        android:textColor="?android:attr/textColorPrimary" />
                </LinearLayout>

            </RelativeLayout>

        </LinearLayout>

    </LinearLayout>

</com.google.android.material.card.MaterialCardView>
```

**引入天气界面的各部分布局到主界面中**
```xml
<androidx.drawerlayout.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/drawerLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
        android:id="@+id/swipeRefresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ScrollView
            android:id="@+id/weatherLayout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:overScrollMode="never"
            android:scrollbars="none"
            android:visibility="invisible">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical">

                <include layout="@layout/now" />

                <include layout="@layout/forecast" />

                <include layout="@layout/life_index" />

            </LinearLayout>

        </ScrollView>

    </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:clickable="true"
        android:focusable="true"
        android:background="@color/purple_200">

        <fragment
            android:id="@+id/placeFragment"
            android:name="com.bo.boweather.android.ui.place.PlaceFragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_marginTop="25dp"/>

    </FrameLayout>

</androidx.drawerlayout.widget.DrawerLayout>
```

**彩云天气返回的数据中，天气情况都是一些像CLOUDY，WIND之类的天气代码，所以我们还需要把这些天气代码转换成一个Sky对象**
```kotlin
//Sky数据模型，包含info(天气情况对应的文字),icon(图标),bg(背景)
class Sky(val info:String,val icon:Int,val bg:Int) {}

private val sky = mapOf(
    "CLEAR_DAY" to Sky("晴", R.drawable.ic_clear_day, R.drawable.bg_clear_day),
    "CLEAR_NIGHT" to Sky("晴", R.drawable.ic_clear_night, R.drawable.bg_clear_night),
    "PARTLY_CLOUDY_DAY" to Sky("多云", R.drawable.ic_partly_cloud_day, R.drawable.bg_partly_cloudy_day),
    "PARTLY_CLOUDY_NIGHT" to Sky("多云", R.drawable.ic_partly_cloud_night, R.drawable.bg_partly_cloudy_night),
    "CLOUDY" to Sky("阴", R.drawable.ic_cloudy, R.drawable.bg_cloudy),
    "WIND" to Sky("大风", R.drawable.ic_cloudy, R.drawable.bg_wind),
    "LIGHT_RAIN" to Sky("小雨", R.drawable.ic_light_rain, R.drawable.bg_rain),
    "MODERATE_RAIN" to Sky("中雨", R.drawable.ic_moderate_rain, R.drawable.bg_rain),
    "HEAVY_RAIN" to Sky("大雨", R.drawable.ic_heavy_rain, R.drawable.bg_rain),
    "STORM_RAIN" to Sky("暴雨", R.drawable.ic_storm_rain, R.drawable.bg_rain),
    "THUNDER_SHOWER" to Sky("雷阵雨", R.drawable.ic_thunder_shower, R.drawable.bg_rain),
    "SLEET" to Sky("雨夹雪", R.drawable.ic_sleet, R.drawable.bg_rain),
    "LIGHT_SNOW" to Sky("小雪", R.drawable.ic_light_snow, R.drawable.bg_snow),
    "MODERATE_SNOW" to Sky("中雪", R.drawable.ic_moderate_snow, R.drawable.bg_snow),
    "HEAVY_SNOW" to Sky("大雪", R.drawable.ic_heavy_snow, R.drawable.bg_snow),
    "STORM_SNOW" to Sky("暴雪", R.drawable.ic_heavy_snow, R.drawable.bg_snow),
    "HAIL" to Sky("冰雹", R.drawable.ic_hail, R.drawable.bg_snow),
    "LIGHT_HAZE" to Sky("轻度雾霾", R.drawable.ic_light_haze, R.drawable.bg_fog),
    "MODERATE_HAZE" to Sky("中度雾霾", R.drawable.ic_moderate_haze, R.drawable.bg_fog),
    "HEAVY_HAZE" to Sky("重度雾霾", R.drawable.ic_heavy_haze, R.drawable.bg_fog),
    "FOG" to Sky("雾", R.drawable.ic_fog, R.drawable.bg_fog),
    "DUST" to Sky("浮尘", R.drawable.ic_fog, R.drawable.bg_fog)
)

fun getSky(skycon: String):Sky{
    return sky[skycon]?:sky["CLEAR_DAY"]!!
}
```

**到WeatherActivity中请求天气数据,并把数据展示到界面上**
```kotlin
class WeatherActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        //首先从intent 中取出地区的经纬度的名称
        if(viewModel.locationLng.isEmpty()){
            viewModel.locationLng=intent.getStringExtra("location_lng")?:""
        }
        if(viewModel.locationlat.isEmpty()){
            viewModel.locationlat=intent.getStringExtra("location_lat")?:""
        }
        if(viewModel.placeName.isEmpty()){
            viewModel.placeName=intent.getStringExtra("place_name")?:""
        }

        //开始监控weatherLiveData，当发生变化的时候就是获取到了信息
        viewModel.weatherLiveData.observe(this, Observer { result->
            val weather=result.getOrNull()
            if(weather!=null){
                showWeatherInfo(weather)//获取到信息之后就把信息展示到UI上
            }else{
                Toast.makeText(this,"无法成功获取天气信息",Toast.LENGTH_SHORT)
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationlat)//刷新天气的请求
    }

    private fun showWeatherInfo(weather: Weather) {
        placeName.text=viewModel.placeName
        val realtime=weather.realtime
        val daily=weather.daily

        //填充now.xml中的数据
        val currentTempText="${realtime.temperature.toInt()}"
        currentTemp.text=currentTempText
        currentSky.text=getSky(realtime.skycon).info

        val currentPM25Text="空气指数 ${realtime.air_quality.aqi.chn.toInt()}"
        currentAQI.text=currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)

        //填充forecast.xml数据
        forecastLayout.removeAllViews()
        val days=daily.skycon.size
        for (i in 0 until days){//由于获取的是未来几天的数据，就用一个foreach循环来获取每一天
            val skycon=daily.skycon[i]
            val temperature=daily.temperature[i]//获取那一天的天气
            val view=LayoutInflater.from(this).inflate(R.layout.forecast_item
                ,forecastLayout,false)
            val dateInfo=view.findViewById(R.id.dateInfo) as TextView
            val skyIcon=view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo=view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo=view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat=SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())//把日期格式化
            dateInfo.text=simpleDateFormat.format(skycon.date)
            val sky= getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text=sky.info
            val tempText="${temperature.min.toInt()}~${temperature.max.toInt()} 摄氏度"
            temperatureInfo.text=tempText
            forecastLayout.addView(view)
        }

        //填充life_index.xml中的数据
        val lifeIndex=daily.lifeIndex
        coldRiskText.text=lifeIndex.coldRisk[0].desc//只要当天的生活指数
        dressingText.text=lifeIndex.dressing[0].desc
        ultravioletText.text=lifeIndex.ultraviolet[0].desc
        carWashingText.text=lifeIndex.carWashing[0].desc
        weatherLayout.visibility=View.VISIBLE
    }
}
```

**要能从搜索城市界面跳转到天气界面**
```kotlin
class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)

        val holder=ViewHolder(view)
        holder.itemView.setOnClickListener {//给place_item一个点击事件,当点击了地址就会跳转到天气界面
            val position=holder.adapterPosition
            val place=placeList[position]

            val intent=Intent(parent.context,WeatherActivity::class.java).apply { 
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            fragment.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount() = placeList.size
}
```

**然后就可以运行试试了**


**简单实现背景图和状态栏融合**
```kotlin
class WeatherActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ·······························································
        val decorView=window.decorView//获取到当前Activity的DecorView(Android视图树的根节点视图)
        decorView.systemUiVisibility =
             View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                     View.SYSTEM_UI_FLAG_LAYOUT_STABLE//布局回显示在状态栏上面
        window.statusBarColor=Color.TRANSPARENT//状态栏变透明
        ···
    }
}
```

**将布局上下移一点让状态栏显示出来**
```xml
android:fitsSystemWindows表示为状态栏留出空间
```

### 记录选中的城市

**用SharedPerferences把上次查询的城市储存起来**
```kotlin
object PlaceDao {
    private fun sharedPreferences(): SharedPreferences {
        //获取SharedPreferences对象
        return BoWeatherApplication.context.getSharedPreferences("bo_weather",Context.MODE_PRIVATE)
    }
    fun savePlace(place:Place){
        //把place对象通过Gson对象转化成Json字符串，存到sharedPreferences中
        sharedPreferences().edit{
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavePlace(): Place {
        //从sharedPreferences中获取json字符串用Gson转化成Place对象
        val placeJson= sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    //判断sharedPreferences中是否有Place已经存储
    fun isPlaceSaved()= sharedPreferences().contains("place")
}
```

**仓库层中实现以下保存**
```kotlin
object Repository {

    fun savePlace(place: Place)=PlaceDao.savePlace(place)

    fun getSavedPlace()=PlaceDao.getSavedPlace()

    fun isSavedPlace()=PlaceDao.isPlaceSaved()
    ···
}
```
其实这里的实现方式并不标准，因为即使是用ShardedPreferences文件进行读写，也不建议在主线程中进行，虽然他的执行速度通常会很快，但是最佳的实现方式肯定还是开启一个线程来执行这些耗时的任务，然后通过LiveData对数据返回，这样写只是为了简单一点，但是**希望你自己能尝试以下最佳写法**

**这几个接口的业务逻辑是和PlaceModel相关的，所以还需要再PlaceViewModel中封装一层才行**
```kotlin
class PlaceViewModel:ViewModel() {

    fun getSavedPlace()=Repository.getSavedPlace()

    fun savePlace(place: Place)=Repository.savePlace(place)

    fun isSavedPlace()=Repository.isSavedPlace()
    ··· 
}
```
仓库层中几个接口没有开启线程，因此不用借助LiveData对象来观察数据，直接调用接口


**具体的调用函数来实现**

**更改PlaceAdapter**
```kotlin
class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    ···

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ···
        holder.itemView.setOnClickListener {
            ···
            fragment.viewModel.savePlace(place)//保存地区
            fragment.startActivity(intent)
            fragment.activity?.finish()//如果跳转到天气界面的话就把当前的这个地区列表布局Activity退出栈，资源还没有释放
        }
        ···
    }
    ···
}
```

**读取地区数据，如果能读到就直接开这个地区的天气**
```kotlin
class PlaceFragment : Fragment() {

    ···
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        //如果是在MainActivity中并且有保存的地区数组才会打开默认的地区天气页面
        if(activity is MainActivity&&viewModel.isSavedPlace()){
            //kotlin中API提供的 is 运算符类似于Java中的 instanceof 关键字的用法。
            //is 运算符可以检查对象是否与特定的类型兼容(兼容：此对象是该类型，或者派生类)，
            //同时也用来检查对象（变量）是否属于某数据类型（如Int、String、Boolean等）。
            //!is运算符是它的否定形式。

            val place=viewModel.getSavedPlace()
            val intent= Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return

        }
        ···
    }
}
```

## 手动刷新天气和切换城市

### 手动刷新天气

**在activity_waether.xml中添加下拉控件**
```xml
<!--SwipeRefreshLayout实现下拉控件-->
<androidx.swiperefreshlayout.widget.SwipeRefreshLayout
        android:id="@+id/swipeRefresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ScrollView
            ···">

            ···

        </ScrollView>

</androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
```

**在WeatherActivity中添加刷新天气的处理逻辑**
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val decorView=window.decorView//获取到当前Activity的DecorView(Android视图树的根节点视图)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE//布局回显示在状态栏上面
        window.statusBarColor= Color.TRANSPARENT//状态栏变透明

        setContentView(R.layout.activity_weather)

        //首先从intent 中取出地区的经纬度的名称
        if(viewModel.locationLng.isEmpty()){//只有viewModel中的位置信息是空的时候才会更新
            viewModel.locationLng=intent.getStringExtra("location_lng")?:""
        }
        if(viewModel.locationlat.isEmpty()){
            viewModel.locationlat=intent.getStringExtra("location_lat")?:""
        }
        if(viewModel.placeName.isEmpty()){
            viewModel.placeName=intent.getStringExtra("place_name")?:""
        }

        //开始监控weatherLiveData，当发生变化的时候就是获取到了信息
        viewModel.weatherLiveData.observe(this, Observer { result->
            val weather=result.getOrNull()
            if(weather!=null){
                showWeatherInfo(weather)//获取到信息之后就把信息展示到UI上
            }else{
                Toast.makeText(this,"无法成功获取天气信息",Toast.LENGTH_SHORT)
                result.exceptionOrNull()?.printStackTrace()
            }
            swipeRefresh.isRefreshing=false//当新的天气信息获取到并且显示到UI上之后就结束刷新，并且隐藏刷新条
        })
        swipeRefresh.setColorSchemeResources(R.color.purple_500)//设置刷新条的颜色
        refreshWeather()//这个地方是打开软件的后默认地区显示天气信息的时候刷新一下
        swipeRefresh.setOnRefreshListener {//监听swipeRefresh的刷新事件
            refreshWeather()
        }
    }

    private fun refreshWeather(){
        swipeRefresh.isRefreshing=true//我认为应先标识刷新事件开始，显示刷新条然后再去请求数据
        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationlat)
    }
```

### 切换城市

**由于开始的时候PlaceUI在Fragment中实现，所以现在可以在Activity中插入Fragment，把PlaceUI放在滑动菜单里面可以不遮挡原来的天气界面**

**在now.xml中加一个按钮引导用户使用滑动菜单**
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/nowLayout"
    android:layout_width="match_parent"
    android:layout_height="530dp"
    android:orientation="vertical">

    <FrameLayout
        android:id="@+id/titleLayout"
        android:layout_width="match_parent"
        android:layout_height="70dp"
        android:fitsSystemWindows="true">

        <Button
            android:id="@+id/navBtn"
            android:layout_width="30dp"
            android:layout_height="30dp"
            android:layout_marginStart="15dp"
            android:layout_gravity="center_vertical"
            android:background="@drawable/ic_home" />

        ···
    </FrameLayout>
    ···
</RelativeLayout>
```

**修改activity_weather.xml布局中加入滑动菜单**
```xml
<androidx.drawerlayout.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/drawerLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    ···
    <!--androidx.drawerlayout.widget.DrawerLayout控件下
    指定了android:layout_gravity="start"的就是滑动菜单-->
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:clickable="true"
        android:focusable="true"
        android:background="@color/purple_200">

        <fragment
            android:id="@+id/placeFragment"
            android:name="com.bo.boweather.android.ui.place.PlaceFragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_marginTop="25dp"/>

    </FrameLayout>

</androidx.drawerlayout.widget.DrawerLayout>
```


**修改WeatherActivity加入滑动菜单的内部逻辑**
```kotlin
class WeatherActivity : AppCompatActivity() {
    ···
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        navBtn.setOnClickListener {
            /*一旦点击导航按钮就会打开滑动窗口*/
            drawerLayout.openDrawer(GravityCompat.START)
        }

        //监听整个布局，一旦监听到创建了一个滑动菜单就会监听这个滑动菜单
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener{
            /*监听整个布局，一旦手点击到滑动菜单以外的地方就会关闭滑动菜单*/
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {
                //一旦滑动菜单关闭，那么输入发也会关闭
                val manager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
            }
        })
        ···
    }
}
```


**之前我们是在PlaceFragment中选中的地区之后就跳转到weather界面，但是我们现在是一打开app就是weather界面，所以只需要刷新一下当前的界面的天气信息就可了**
```kotlin
class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>):
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){
    ···
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)

        val holder=ViewHolder(view)
        holder.itemView.setOnClickListener {//给place_item一个点击事件,当点击了地址就会跳转到天气界面
            val position=holder.adapterPosition
            val place=placeList[position]

            val activity=fragment.activity//获取fragment所在的activity中
            if(activity is WeatherActivity){//如果PlaceFragment是在WeatherActivity中
                activity.drawerLayout.closeDrawers()//关闭滑动菜单
                activity.viewModel.locationLng=place.location.lng//更新viewNodel中的地区
                activity.viewModel.locationlat=place.location.lat
                activity.viewModel.placeName=place.name
                activity.refreshWeather()//刷新Weather界面的数据
            }else{
                val intent= Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng",place.location.lng)
                    putExtra("location_lat",place.location.lat)
                    putExtra("place_name",place.name)
                }
                fragment.startActivity(intent)
                activity?.finish()//如果跳转到天气界面的话就把当前的这个地区列表布局Activity退出栈，资源还没有释放
            }
            fragment.viewModel.savePlace(place)//保存地区
        }
        return holder
    }
    ···
}
```

**需要显示导航按钮的背景图片的话，需要改变app主题的标题栏的属性**
```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
    <style name="Theme.随波天气" parent="Theme.MaterialComponents.DayNight.NoActionBar.Bridge">
        ···
    </style>
</resources>
```

## 制作App的图标

## 生成正式签名的APK文件
