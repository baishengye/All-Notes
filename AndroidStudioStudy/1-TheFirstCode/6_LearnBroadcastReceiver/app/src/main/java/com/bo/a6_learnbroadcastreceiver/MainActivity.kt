package com.bo.a6_learnbroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var timeChangeReceiver:TimeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            var intent=Intent("com.bo.a6_learnbroadcastreceiver.MY_BROADCAST")
            /*发出一条"com.bo.a6_learnbroadcastreceiver.MY_BROADCAST"这样的广播*/
            intent.setPackage(packageName)
            /*intent.setPackage至关重要,由于Android8.0之后无法静态注册隐式广播，但是我们的广播接收器就是静态注册的,而且默认情况下就是隐式广播
            所以我们要想办法把我们的广播变成显式广播,我们通过setPackage()指定了广播发给packageName，然后我们的广播就变成了显式广播*/
            /*sendBroadcast(intent)*/
            sendOrderedBroadcast(intent,null)
            /*这里有两个参数，第一个是inent(广播的意图),第二个是权限(null就行)*/
        }


/*intent和intentFilter的区别
        后者比前者多了个筛选作用
        筛选条件:
        action、data和category*/

        val intentFilter=IntentFilter()//定义一个有过滤器的意图
        intentFilter.addAction("android.intent.action.TIME_TICK")
        //给意图添加一个筛选条件(筛选出Action是android.intent.action.TIME_TICK的意图)
        timeChangeReceiver=TimeChangeReceiver()//实例化一个时间改变广播接收器
        registerReceiver(timeChangeReceiver,intentFilter)//注册一个广播接收器timeChangeReveiver来监听intentFilter这个意图
    }

    public fun a(){}

    /*override fun onDestroy() {
        super.onDestroy()
        //当这个活动销毁的时候就把接收器取消注册(一定要取消)
        unregisterReceiver(timeChangeReceiver)
    }*/

    inner class TimeChangeReceiver:BroadcastReceiver(){
        //定义一个接收器继承自BroadcastReceiver,
        override fun onReceive(context: Context, intent: Intent) {
            //当接收到一个消息就Toast一段话
            Toast.makeText(context,"Time Changed",Toast.LENGTH_LONG).show()
            a()

        }
    }
}
