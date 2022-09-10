# `ListView`
```kotlin
最常用,最难用
由于手机屏幕空间比较有限，能够一次性在屏幕上显示的内容并不多，
当我们的程序中有大量的数据需要展示的时候，就可以借助ListView来实现。
ListView允许用户通过手指上下滑动的方式将屏幕外的数据滚动到屏幕内，
同时屏幕上原有的数据会滚动出屏幕。你其实每天都在使用这个控件，
比如查看QQ聊天记录，翻阅微博最新消息，等等。
```
```xml
<ListView
    android:id="@+id/listView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    />
   <!-- 这里引入ListView的时候是和父布局一样宽和一样高
    这样就把整个布局占满了-->
```
```kotlin
class MainActivity : AppCompatActivity() {
    private val data = listOf("Apple","Banana","Orange","watermelon" ,
        "Pear","Grape","Pineapple","Strawberry","Cherry","Mango","Apple",
        "Banana","Orange", "watermelon","Pear", "Grape","Pineapple","Strawberry" , "Cherry","Mango" )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*指定适配器里面的泛型是String
        然后依次传入:Activity的示例,ListView子项布局的id，以及数据源*/
        /*android.R.layout.simple_list_item_1  这是一个Android内置的一个布局文件，里面只有一个TextView，可以用于简单显示一段文本*/
        val adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
        listView.adapter=adapter

        /*适配器（adapter）在android中是数据和视图（View）之间的一个桥梁，
        通过适配器以便于数据在view视图上显示。现在主要有ArrayAdapter、SimpleAdapter、BaseAdapter*/
        /*集合里的数据是无法直接传递给listView的，所以我们需要适配器的帮助*/
    }
}
```
## 定制ListVie页面
```xml
在fruit_item.xml中设计ListView中每一个View的布局

<ImageView
    android:id="@+id/fruitImage"
    android:layout_width="40dp"
    android:layout_height="40dp"
    android:layout_gravity="center_vertical"
    android:layout_marginLeft="10dp"/>

<TextView
    android:id="@+id/fruitName"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center_vertical"
    android:layout_marginLeft="10dp"/>

    <!--这里定义了一个布局，一行会放两个控件，每个控件里的内容都要垂直居中-->
```
```kotlin
class Fruit(val name:String,val imageId:Int) {//水果实体类，作为ListView适配器的适配类型
}

//FruitAdapter定义了一个主构造函数,用于将Activity的实例,ListView子项布局的id和数据源传递过来,作为FruitList和ListView之间的适配器
class FruitAdapter (activity: Activity,val resourceId: Int,data: List<Fruit>):ArrayAdapter<Fruit>(activity,resourceId,data){
    //重写了getView()方法,这个方法在每个子项被滚动到屏幕内的时候会调用
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //from()根据上下文context构建出一个LayoutInflater对象
        // 然后调用inflate()动态加载一个布局文件
        //他有两个参数
        /*第一个:要加载的布局文件的id
        第二个:给加载好的布局再添加一个父布局
        第三个false:表示只让我们在父布局中声明的layout属性生效,但不会为了View添加父布局
        这个才是ListView中的标准写法*/
        val view=LayoutInflater.from(context).inflate(resourceId,parent,false)
        val fruitImage:ImageView=view.findViewById(R.id.fruitImage)
        //这里用findViewById的原因是kotlin-android-extensions在ListView的适配器中是无法工作的，所以只能用findViewById
        val fruitName:TextView=view.findViewById(R.id.fruitName)
        val fruit=getItem(position)//getItem()当前项的Fruit实例
        if (fruit!=null){
            fruitImage.setImageResource(fruit.imageId)
            fruitName.text = fruit.name
        }
        return view
    }
}


class MainActivity : AppCompatActivity() {
    private val fruitList=ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*适配器（adapter）在android中是数据和视图（View）之间的一个桥梁，
        通过适配器以便于数据在view视图上显示。现在主要有ArrayAdapter、SimpleAdapter、BaseAdapter*/
        /*集合里的数据是无法直接传递给listView的，所以我们需要适配器的帮助*/

        initFruits()
        val adapter=FruitAdapter(this,R.layout.fruit_item,fruitList)
        listView.adapter=adapter
    }

    private fun initFruits(){
        repeat(5){
            fruitList.add(Fruit("Apple",R.drawable.apple_pic))
            fruitList.add(Fruit("Banana",R.drawable.banana_pic))
            fruitList.add(Fruit("Orange",R.drawable.orange_pic))
            fruitList.add(Fruit("watermelon",R.drawable.watermelon_pic))
            fruitList.add(Fruit("Pear",R.drawable.pear_pic))
            fruitList.add(Fruit("Pineapple",R.drawable.pineapple_pic))
        }
    }
}
```

## 提高ListView的运行效率
```kotlin
目前的ListView效率还很低
其实getView()方法中还有一个参数convertView，这个参数用于将之前加载好的布局进行了缓存

我们可以在getView()方法中加上几句话优化加载布局
//convertView存放的是布局
val view:View
if(convertView==null){//这样写就可以只创建一次view了,其他次就可以直接从convertView中读取布局
    view=LayoutInflater.from(context).inflate(resourceId,parent,false)
}else{
    view=convertView
}

但这样还不够，因为而每次都会调用View的getViewById()方法来获取一次控件的实例
我们可以借助ViewHolder来对这部分进行优化
FruitAdapter中改为
inner class ViewHolder(val fruitImage: ImageView,var fruitName:TextView){}

override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view:View
    val viewHolder:ViewHolder
    if(convertView==null){//这样写就可以只创建一次view了,其他次就可以直接从convertView中读取布局
        view=LayoutInflater.from(context).inflate(resourceId,parent,false)
        val fruitImage:ImageView=view.findViewById(R.id.fruitImage)
        //这里用findViewById的原因是kotlin-android-extensions在ListView的适配器中是无法工作的，所以只能用findViewById
        val fruitName:TextView=view.findViewById(R.id.fruitName)
        viewHolder=ViewHolder(fruitImage,fruitName)
        view.tag=viewHolder
        //这里其实是把viewHolder中的信息储存到了view的tag中,因为view这个布局是界面在就一直存在,那么fruitImage,fruitName就保存下来了
    }else{
        view=convertView
        viewHolder=view.tag as ViewHolder
        //在不用重新生成布局的时候说明布局已经存在了，那么viewHolder中的信息就一定已经储存在tag里了，
        /*我们只需要取出来用就行了*/
    }

    val fruit=getItem(position)//getItem()当前项的Fruit实例
    if (fruit!=null){
        viewHolder.fruitImage.setImageResource(fruit.imageId)
        viewHolder.fruitName.text = fruit.name
    }
    return view
}
```

## `ListView的点击事件`
```kotlin
在MainActivity的onCreate()

//个体ListView注册了一个监听事件,如果用户点击了ListView的任意一个子项，就会回调到Lambda表达式中，
listView.setOnItemClickListener { parent, view, position, id ->
    //通过传回来的position判断是点击了哪个子项
    var fruit= fruitList[position]
    Toast.makeText(this,fruit.name,Toast.LENGTH_SHORT).show()
}
```