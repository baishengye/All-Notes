package com.bo.a4_learnui_5_learnrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bo.a4_learnui_4_learnlistview.Fruit
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private val fruitList=ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFruits()
        //创建一个LinearLayoutManager对象
        /*val layoutManager=LinearLayoutManager(this)*/
        //并将其设置到recyclerView当中
        /*layoutManager指定了recyclerView用啥布局，LinearLayoutManager是线性布局的意思，可以实现ListView类似效果*/
        /*recyclerView.layoutManager=layoutManager*/
        /*layoutManager.orientation=LinearLayoutManager.HORIZONTAL*/

        /*StaggeredGridLayoutManager构造一个layoutManager的时候
        第一个参数:指定布局的列数
        第二个参数:会指定布局的排列方向StaggeredGridLayoutManager.VERTICAL表示纵向布局*/
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