package com.bo.a4_learnui_5_learnrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bo.a4_learnui_4_learnlistview.Fruit

class FruitAdapter (private val fruitList: List<Fruit>):RecyclerView.Adapter<FruitAdapter.ViewHolder>(){
    //实现一个内部类ViewHolder，继承自RecyclerView.ViewHolder，保存要展示的数据
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val fruitImage:ImageView=view.findViewById(R.id.fruitImage)
        val fruitName:TextView=view.findViewById(R.id.fruitName)
    }

    //加载ViewHolder实例,并且吧加载好的布局传入构造函数当中，最后将ViewHolder实例返回
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.fruit_item,parent,false)

        val viewHolder=ViewHolder(view)
        //这个地方是给最外层布局注册点击事件,itemView表示最外层布局
        viewHolder.itemView.setOnClickListener {
            //获取点击的position
            val position=viewHolder.adapterPosition
            val fruit=fruitList[position]
            Toast.makeText(parent.context,"你点击的view是${fruit.name}",Toast.LENGTH_SHORT).show()
        }

        viewHolder.fruitImage.setOnClickListener {
            val position=viewHolder.adapterPosition
            val fruit=fruitList[position]
            Toast.makeText(parent.context,"你点击的图片是${fruit.name}",Toast.LENGTH_SHORT).show()
        }
        return viewHolder
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


/*
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

    */
/*用于对RecyclerView的子项进行赋值数据，会在每个子项被滚动到屏幕内的时候执行*//*

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //这里通过position参数找到当前项的Fruit实例
        val fruit=fruitList[position]
        //再把数据设置到ViewHolder当中
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text=fruit.name
    }

    override fun getItemCount(): Int = fruitList.size
        //告诉RecyclerView有多少子项
}*/
