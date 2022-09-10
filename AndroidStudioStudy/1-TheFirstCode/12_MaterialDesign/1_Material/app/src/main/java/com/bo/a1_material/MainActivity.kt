package com.bo.a1_material

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bo.a1_material.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.time.Duration
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fruits=mutableListOf(
        Fruit("Apple", R.drawable.apple),
        Fruit("Banana", R.drawable.banana),
        Fruit("Orange", R.drawable.orange),
        Fruit("Watermelon", R.drawable.watermelon),
        Fruit("Pear", R.drawable.pear),
        Fruit("Grape", R.drawable.grape),
        Fruit("Pineapple", R.drawable.pineapple),
        Fruit("Strawberry", R.drawable.strawberry),
        Fruit("Cherry", R.drawable.cherry),
        Fruit("Mango", R.drawable.mango))

    private val fruitList=ArrayList<Fruit>()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.let {//获得ActionBar的实例
            it.setDisplayHomeAsUpEnabled(true)//当ActionBar不为空的时候就让导航按钮显示出来
            it.setHomeAsUpIndicator(R.drawable.ic_menu)//设置一个导航按钮图标(默认是叫做home按钮,默认的图标是返回箭头，含义是返回上一个Activity)
        }

        //默认选中R.id.navCall
        binding.navView.setCheckedItem(R.id.navCall)
        //菜单项选中事件的监听器，当用户点击了任意菜单项时，就会回调到传入的lambda表达式
        binding.navView.setNavigationItemSelectedListener {
            binding.drawLayout.closeDrawers()
            true
        }

        /*binding.fab.setOnClickListener {
            Toast.makeText(this,"点击了悬浮按钮",Toast.LENGTH_SHORT).show()
        }*/

        binding.fab.setOnClickListener {

            it.showSnackbar("删除数据","Undo") {
                Toast.makeText(this,"123456",Toast.LENGTH_LONG).show()
            }

            /*Snackbar.make(it, "12", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                    "1123".showToast(this)
                }.show()*/
        }

        initFruits()
        /*GridLayoutManager标准网格布局的布局管理器,此处是指在这个布局中，一行两个，对齐高度一致*/
        val layoutManager= GridLayoutManager(this,2)
        binding.recyclerView.layoutManager=layoutManager
        val adapter=FruitAdapter(this,fruitList)
        binding.recyclerView.adapter=adapter

        binding.swipeRefresh.setColorSchemeResources(R.color.purple_700)
        //设置刷新时进度条的颜色
        binding.swipeRefresh.setOnRefreshListener {
            //下拉刷新的监听器
            refreshFruits(adapter)
        }
    }

    private fun refreshFruits(adapter: FruitAdapter){
        //在子线程中开启对UI的操作
        thread {
            Thread.sleep(1000)//让子线程沉睡1秒，不然看不到刷新的效果
            runOnUiThread { //要借助异步消息处理机制，让UI操作在主线程中进行
                initFruits()
                adapter.notifyDataSetChanged()//通过适配器来刷新数据变化
                binding.swipeRefresh.isRefreshing=false//表示刷新事件结束了，并且会隐藏刷新进度条,默认是true不结束不隐藏
            }
        }
    }

    private fun initFruits(){
        fruitList.clear()
        repeat(50){
            val index=(0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            /*home按钮的id永远是android.R.id.home*/
            android.R.id.home->binding.drawLayout.openDrawer(GravityCompat.START)
            R.id.backup->Toast.makeText(this,"点击了backup",Toast.LENGTH_SHORT).show()
            R.id.delete->Toast.makeText(this,"点击了delete",Toast.LENGTH_SHORT).show()
            R.id.setting->Toast.makeText(this,"点击了setting",Toast.LENGTH_SHORT).show()
        }
        return true
    }
}