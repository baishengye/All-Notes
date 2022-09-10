package com.bo.a4_learnui_4_learnlistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /*private val data = listOf("Apple","Banana","Orange","watermelon" ,
                "Pear","Grape","Pineapple","Strawberry","Cherry","Mango","Apple",
                "Banana","Orange", "watermelon","Pear", "Grape","Pineapple","Strawberry" , "Cherry","Mango" )*/

    private val fruitList=ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*指定适配器里面的泛型是String
        然后依次传入:Activity的示例,ListView子项布局的id，以及数据源*/
        /*android.R.layout.simple_list_item_1  这是一个Android内置的一个布局文件，里面只有一个TextView，可以用于简单显示一段文本*/
        /*val adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
        listView.adapter=adapter*/

        /*适配器（adapter）在android中是数据和视图（View）之间的一个桥梁，
        通过适配器以便于数据在view视图上显示。现在主要有ArrayAdapter、SimpleAdapter、BaseAdapter*/
        /*集合里的数据是无法直接传递给listView的，所以我们需要适配器的帮助*/

        initFruits()
        val adapter=FruitAdapter(this,R.layout.fruit_item,fruitList)
        listView.adapter=adapter


        //个体ListView注册了一个监听事件,如果用户点击了ListView的任意一个子项，就会回调到Lambda表达式中，
        listView.setOnItemClickListener { parent, view, position, id ->
            //通过传回来的position判断是点击了哪个子项
            var fruit= fruitList[position]
            Toast.makeText(this,fruit.name,Toast.LENGTH_SHORT).show()
        }
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