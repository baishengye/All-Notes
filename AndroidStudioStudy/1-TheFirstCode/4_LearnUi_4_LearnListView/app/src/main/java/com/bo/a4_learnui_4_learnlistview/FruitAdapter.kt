package com.bo.a4_learnui_4_learnlistview

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

//FruitAdapter定义了一个主构造函数,用于将Activity的实例,ListView子项布局的id和数据源传递过来
class FruitAdapter (activity: Activity,val resourceId: Int,data: List<Fruit>):ArrayAdapter<Fruit>(activity,resourceId,data){

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

    //重写了getView()方法,这个方法在每个子项被滚动到屏幕内的时候会调用
    /*override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //convertView存放的是布局
        val view:View
        if(convertView==null){//这样写就可以只创建一次view了,其他次就可以直接从convertView中读取布局
            view=LayoutInflater.from(context).inflate(resourceId,parent,false)
        }else{
            view=convertView
        }
        //from()根据上下文context构建出一个LayoutInflater对象
        // 然后调用inflate()动态加载一个布局文件
        //他有两个参数
        *//*第一个:要加载的布局文件的id
        第二个:给加载好的布局再添加一个父布局
        第三个false:表示只让我们在父布局中声明的layout属性生效,但不会为了View添加父布局
        这个才是ListView中的标准写法*//*
        *//*val view=LayoutInflater.from(context).inflate(resourceId,parent,false)*//*
        val fruitImage:ImageView=view.findViewById(R.id.fruitImage)
        //这里用findViewById的原因是kotlin-android-extensions在ListView的适配器中是无法工作的，所以只能用findViewById
        val fruitName:TextView=view.findViewById(R.id.fruitName)
        val fruit=getItem(position)//getItem()当前项的Fruit实例
        if (fruit!=null){
            fruitImage.setImageResource(fruit.imageId)
            fruitName.text = fruit.name
        }
        return view
    }*/
}