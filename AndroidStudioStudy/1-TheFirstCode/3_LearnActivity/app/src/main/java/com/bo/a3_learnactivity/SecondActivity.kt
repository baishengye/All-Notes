package com.bo.a3_learnactivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.second_layout.*

class SecondActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_layout)

        /*这里intent实际上是使用的父类的getIntent()方法,该方法会获取用于启动SecondActivity的Intent
        然后调用getStringExtra()获取字符串类型的数据*//*
        var extraData=intent.getStringExtra("extra_data")
        Log.d("SecondActivity","FirstActivity say $extraData")*/

        /*button2.setOnClickListener {
            var intent=Intent()//这个Intent只是为了携带数据，不具有指定Activity的功能
            intent.putExtra("return_data","SecondActivity say Bey FirstActivity")//返回数据的名称,返回数据里的内容
            setResult(RESULT_OK,intent)
            *//*setResult()专门用于向上一个Activity返回数据,他会接受2个参数:
            第一个参数用于向上一个Activity返回处理结果,一般使用RESULT_OK或者RESULT_CANCELED
            第二个参数则是把带有数据的Intent传递回去*//*
            finish()
        }*/

        /*Log.d("SecondActivity","Task id is $taskId")*/
        button2.setOnClickListener {
            var intent=Intent(this,ThirdActivity::class.java)
            startActivity(intent)
        }
    }

    companion object{
        fun actionStart(context: Context, data1:String, data2:String){
            var intent=Intent(context,SecondActivity::class.java)
            intent.putExtra("param1",data1)
            intent.putExtra("param2",data2)
            context.startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SecondActivity","onDestroy")
    }

    override fun onBackPressed() {
        var intent=Intent()
        intent.putExtra("return_data","SecondActivity say Bey FirstActivity")
        setResult(RESULT_OK,intent)
        finish()
    }
}