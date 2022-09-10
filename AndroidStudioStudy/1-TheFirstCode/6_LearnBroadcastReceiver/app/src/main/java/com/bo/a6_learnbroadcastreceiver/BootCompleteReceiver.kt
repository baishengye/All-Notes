package com.bo.a6_learnbroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Toast.makeText(context,"程序启动了",Toast.LENGTH_LONG).show()
        /*但是注意在onReceive中不要使用太复杂的逻辑因为BroadcastReceiver中不允许开启线程
        当onReceive运行太久而没结束程序就会出现错误*/
    }

}