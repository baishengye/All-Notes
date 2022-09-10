package com.bo.a1_helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {//MainActivity只有在继承Activity类或者子类的时候才能拥有Activity的特性
    override fun onCreate(savedInstanceState: Bundle?) {//创建一个活动
        super.onCreate(savedInstanceState)//调用父类中的OnCreate函数,创造了app的开始的空白页面
        setContentView(R.layout.activity_main)//想要显示Hello World还需要在空白页面中显示布局R.layout.activity_main,布局是在res.layout文件夹下定义
        Log.d("MainActivity","onCreate execute");
        //第一个参数是指定想要打印Log的类,第二个参数是指想要答应的具体内容
        //execute 就是查询
    }
}