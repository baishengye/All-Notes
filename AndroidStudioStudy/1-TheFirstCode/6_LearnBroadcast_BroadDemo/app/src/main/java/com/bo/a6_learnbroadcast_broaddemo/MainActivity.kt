package com.bo.a6_learnbroadcast_broaddemo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forceOffline.setOnClickListener {
            //Toast.makeText(this,"点击了强制下线", Toast.LENGTH_LONG).show()
            val intent= Intent("com.bo.a6_learnbroadcast_broaddemo.FORCE_OFFLINE")
            sendBroadcast(intent)
        }
    }
}