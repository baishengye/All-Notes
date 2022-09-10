# RecyclerView
```kotlin
可以轻松实现和ListView一样的效果，还优化了ListView各种不足之处
Android也更加推荐使用RecylerView
```
## 基本用法
```kotlin
RecyclerView属于是新增控件，为此，谷歌将其定义在了AndroidX当中
我们需要去项目的build.gradle中添加RecyclerView依赖才行

implementation 'androidx.recyclerview:recyclerview:1.0.0'
```
```xml
实现一下

<!--RecyclerView不是内置在系统SDK中的，所以需要把完整的包路径写出来
    此时定义的宽度和高度会把父布局的空间占满-->
    <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/recyclerView"/>
```
```kotlin
fruit_item.xml和Fruit.kt就用ListView中的就好了
```
```kotlin
重要的是适配器怎么写
class FruitAdapter (val fruitList: List<Fruit>):RecyclerView.Adapter<FruitAdapter.ViewHolder>(){
    //实现一个内部类ViewHolder，继承自RecyclerView.ViewHolder，保存要展示的数据
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val fruitImage:ImageView=view.findViewById(R.id.fruitImage)
        val fruitName:TextView=view.findViewById(R.id.fruitName)
    }

    //加载ViewHolder实例,并且吧加载好的布局传入构造函数当中，最后将ViewHolder实例返回
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.fruit_item,parent,false)
        return ViewHolder(view)
    }

    /*用于对RecyclerView的子项进行赋值数据，会在每个子项被滚动到屏幕内的时候执行*/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //这里通过position参数找到当前项的Fruit实例
        val fruit=fruitList[position]
        //再把数据设置到ViewHolder当中
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text=fruit.name
    }

    override fun getItemCount(): Int = fruitList.size
        //告诉RecyclerView有多少子项
}
```
```kotlin
MainActivity中

class MainActivity : AppCompatActivity() {
    private val fruitList=ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFruits()
        //创建一个LinearLayoutManager对象
        val layoutManager=LinearLayoutManager(this)
        //并将其设置到recyclerView当中
        /*layoutManager指定了recyclerView用啥布局，LinearLayoutManager是线性布局的意思，可以实现ListView类似效果*/
        recyclerView.layoutManager=layoutManager
        val adapter=FruitAdapter(fruitList)
        recyclerView.adapter=adapter

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
## 实现横向和瀑布流布局
```kotlin
ListView不能实现横向布局，但是recyclerView可以
```
```xml
只需要把fruit_item.xml改成

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="80dp"
    android:layout_height="match_parent">

    <ImageView
        android:id="@+id/fruitImage"
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="10dp"/>

    <TextView
        android:id="@+id/fruitName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="10dp"/>

    <!--这里定义了一个布局，一行会放两个控件，每个控件里的内容都要垂直居中-->
</LinearLayout>
```
```kotlin
把MainActivity的onCreate()中加一句
//其实就是把线性布局的方式改成了水平
layoutManager.orientation=LinearLayoutManager.HORIZONTAL
```
```kotlin
为什么ListView那么不容易实现，而recyclerView很容易呢？
因为ListView的布局排列是由自身去管理的，而recyclerView则把这个工作交给了LayoutManager
LayoutManager有一系列可扩展的布局排列接口，子类只需要按照接口的规范来实现，就可以定制各种不同的排列方式的布局了
```
## 瀑布流布局
```xml
fruit_item.xml修改成

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="5dp">

    <ImageView
        android:id="@+id/fruitImage"
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="10dp"/>

    <TextView
        android:id="@+id/fruitName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="left"
        android:layout_marginTop="10dp"/>

    <!--这里定义了一个布局，一行会放两个控件，每个控件里的内容都要垂直居中-->
</LinearLayout>
```
```kotlin
MainActivity修改成

class MainActivity : AppCompatActivity() {
    private val fruitList=ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFruits()

        val layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager=layoutManager
        val adapter=FruitAdapter(fruitList)
        recyclerView.adapter=adapter

    }

    private fun getRandomLengthString(str:String):String{
        val n=(1..20).random()
        val builder=StringBuilder()
        repeat(n){
            builder.append(str)
        }
        return builder.toString()
    }

    private fun initFruits(){
        repeat(5){
            fruitList.add(Fruit(getRandomLengthString("Apple"),R.drawable.apple_pic))
            fruitList.add(Fruit(getRandomLengthString("Banana"),R.drawable.banana_pic))
            fruitList.add(Fruit(getRandomLengthString("Orange"),R.drawable.orange_pic))
            fruitList.add(Fruit(getRandomLengthString("watermelon"),R.drawable.watermelon_pic))
            fruitList.add(Fruit(getRandomLengthString("Pear"),R.drawable.pear_pic))
            fruitList.add(Fruit(getRandomLengthString("Pineapple"),R.drawable.pineapple_pic))
        }
    }
}
```