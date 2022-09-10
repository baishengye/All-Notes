package com.bo.a6_learnbroadcast_broaddemo

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.app.AlertDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

open class BaseActivity:AppCompatActivity() {

    private lateinit var receiver: ForceOfflineReceiver
    //    创建一个新的活动时, 就把这个活动交给ActivityCollector进行管理
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.bo.a6_learnbroadcast_broaddemo.FORCE_OFFLINE")
        receiver = ForceOfflineReceiver()
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }
    //销毁一个活动时,  把这个活动从ActivityCollector中移除
    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    inner class ForceOfflineReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context,intent: Intent) {
            Toast.makeText(context,"接收到了广播",Toast.LENGTH_LONG)
            /*弹出一个对话框，提示*/
            AlertDialog.Builder(context).apply {
                setTitle("警告")
                setMessage("你将被强制下线,请重新登录")
                setCancelable(false)//将对话框设置成不能呗取消
                setPositiveButton("OK"){_,_ ->//当用户点击OK的时候就会
                    ActivityCollector.finishAll()//销毁所有的Activity
                    val i=Intent(context, LoginActivity::class.java)//定义一个打开登录页面的意图
                    context.startActivity(i)//重新打开登录界面
                }
                show()
            }
        }

    }
}