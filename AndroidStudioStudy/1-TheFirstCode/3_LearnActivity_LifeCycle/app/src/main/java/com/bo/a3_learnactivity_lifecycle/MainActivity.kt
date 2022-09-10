package com.bo.a3_learnactivity_lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity","onCreate")
        setContentView(R.layout.activity_main)

        startNormalActivity.setOnClickListener {
            var intent=Intent(this,NormalActivity::class.java)
            startActivity(intent)
        }

        startDialogActivity.setOnClickListener {
            var intent=Intent(this,DialogActivity::class.java)
            startActivity(intent)
        }

        //在Activity被系统回收之前，你通过savedInstanceState()方法保存数据
        //savedInstanceState参数会带有之前保存的全部数据,然后用相应的方法取值即可
        if (savedInstanceState!=null){
            val tempData=savedInstanceState.getString("data_key")
            if (tempData != null) {
                Log.d("MainActivity",tempData)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        /*这个方法携带一个Bundle类型的参数，Bundle提供了一系列方法来保存数据
        比如:putString()保存字符串:有两个参数
            outState:键,用于后面从Bundle中取值
            outPersistentState:真正要保存的内容*/
        super.onSaveInstanceState(outState, outPersistentState)
        var tempData="you typed"
        outState.putString("data_key",tempData)
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity","onRestart")
    }
}