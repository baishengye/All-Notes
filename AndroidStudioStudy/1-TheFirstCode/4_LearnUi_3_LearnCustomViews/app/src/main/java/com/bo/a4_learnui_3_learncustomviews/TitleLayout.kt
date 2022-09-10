package com.bo.a4_learnui_3_learncustomviews

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.title.view.*

class TitleLayout (context: Context,attrs: AttributeSet):LinearLayout(context,attrs){
    //这里的构造函数接受了两个参数
    /*Context:上下文
    AttributeSet:系统属性的一个集合*/
    init {
        LayoutInflater.from(context).inflate(R.layout.title,this)
        //from()根据上下文构建出一个LayoutInflater对象
        // 然后调用inflate()动态加载一个布局文件
        //他有两个参数
        /*第一个:要加载的布局文件的id
        第二个:给加载好的布局再添加一个父布局*/

        titleBack.setOnClickListener{
            val activity=context as Activity //把context上下文转化成了一个Activity
            //as是kotlin中的强制类型转换符
            activity.finish()
        }

        titleEdit.setOnClickListener {
            Toast.makeText(context,"你点击了按钮",Toast.LENGTH_SHORT).show()
        }
    }
    //现在我们每当在一个布局中引入TitleLayout的时候，Back和Edit按钮都已经自动实现好了
}