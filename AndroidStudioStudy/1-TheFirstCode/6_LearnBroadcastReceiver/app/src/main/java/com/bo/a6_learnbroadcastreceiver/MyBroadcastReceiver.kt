package com.bo.a6_learnbroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context,"在定义的广播接收器中接收到了广播",Toast.LENGTH_LONG).show()
        abortBroadcast()//表示将广播截断，后面的广播接收器就接收不到广播了
    }
}