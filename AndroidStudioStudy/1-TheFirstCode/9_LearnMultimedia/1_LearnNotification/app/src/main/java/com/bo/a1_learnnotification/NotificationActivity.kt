package com.bo.a1_learnnotification

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //进入这个活动之后就会关闭Id是1的通知
        /*manager.cancel(1)*/
        manager.cancel(2)
    }
}