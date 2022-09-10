package com.bo.a3_learnactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.second_layout.*
import kotlinx.android.synthetic.main.third_layout.*

class ThirdActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_layout)

        button3.setOnClickListener {
            ActivityCollector.finishAll()
            android.os.Process.killProcess(android.os.Process.myPid())//杀死当前程序;myPid()获得当前程序的id参数
        }

        /*Log.d("ThirdActivity","Task id is $taskId")*/
    }
}