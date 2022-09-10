package com.bo.a1_material

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FruitAdapter(private val context: Context, private val fruitList:List<Fruit>):
RecyclerView.Adapter<FruitAdapter.ViewHolder>(){
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val fruitName:TextView=view.findViewById(R.id.fruitName)
        val fruitImage:ImageView=view.findViewById(R.id.fruitImage)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.fruit_item,parent,false)
        val holder=ViewHolder(view)//此处的view是指activity_fruit.xml的整个布局,holder获取到整个布局的情况
        holder.itemView.setOnClickListener { //只要是哪个子控件被点击了，就会获取这个子控件的水果名字和水果图片id
            val position=holder.adapterPosition
            val fruit=fruitList[position]
            val intent=Intent(context,FruitActivity::class.java).apply { //再创建一个意图(打开FruitActivity)
                putExtra(FruitActivity.FRUIT_NAME,fruit.name)//并且把说过名字和水果图片id传过去
                putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.imageId)
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit=fruitList[position]
        holder.fruitName.text=fruit.name
        Glide.with(context).load(fruit.imageId).into(holder.fruitImage)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }
}