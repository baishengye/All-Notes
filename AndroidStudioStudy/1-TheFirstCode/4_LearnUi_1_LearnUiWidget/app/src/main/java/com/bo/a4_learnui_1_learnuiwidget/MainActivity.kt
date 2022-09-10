package com.bo.a4_learnui_1_learnuiwidget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //函数API方式注册一个监听按钮的点击事件
        /*button.setOnClickListener {
            Toast.makeText(this,"click button",Toast.LENGTH_SHORT).show()
        }*/
        /*button.setOnClickListener(this)*/

        /*button.setOnClickListener {
            val inputData=editText.text.toString()//获取editText中的文字
            Toast.makeText(this,inputData,Toast.LENGTH_SHORT).show()//在Toast中显示
        }*/

        /*button.setOnClickListener {
            imageView.setImageResource(R.drawable.img_2)//代码中修改展示的图片资源
        }*/

        /*进度条可不可见

            不可见->可见
            可见->不可见*/
        /*button.setOnClickListener {
            if(progressBar.visibility!=View.GONE)
                progressBar.visibility=View.GONE
            else
                progressBar.visibility=View.VISIBLE
        }*/


        /*button.setOnClickListener {
            progressBar.progress=progressBar.progress+10
        }*/

        button.setOnClickListener(this)
    }

    //通过实现接口的方式注册一个监听按钮的点击事件
    /*override fun onClick(v: View?) {
        when(v?.id){//传进来的View不是空的就进行判断View的id
            R.id.button->{//如果View的id表明他是一个Button的话就执行逻辑
                Toast.makeText(this,"click button",Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button->{
                AlertDialog.Builder(this).apply {//AlertDialog.Builder创建一个对话框
                    setTitle("这是个提示框")//设置对话框的标题
                    setMessage("这里很重要")//对话框里的内容
                    setCancelable(false)//能不能用back关闭对话框: 不能
                    setPositiveButton("可以"){//在对话框里面设置一个确定按钮的点击事件
                        dialog,which->{}//这里两个参数是lambda表达式的参数列表部分,但还不知道有啥用
                    }
                    setNegativeButton("关闭"){//在对话框里面设置一个取消按钮的点击事件
                        dialog,which->{}
                    }
                    show()//把对话框显示出来
                }
            }
        }
    }
}