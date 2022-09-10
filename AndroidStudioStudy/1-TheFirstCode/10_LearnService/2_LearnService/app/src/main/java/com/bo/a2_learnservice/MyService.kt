package com.bo.a2_learnservice

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import kotlin.concurrent.thread

/*
class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    */
/*onCreate，onStartCommand,onDestroy这三个方法是每个Service中最常用到的*//*


    override fun onCreate() {//在Service创建的时候调用
        super.onCreate()
        Log.d("MyService","创建了服务")
    }

    */
/*onstart()方法和onStartCommand()方法的区别：
    onstart()方法是在android2.0一下的版本中使用。而在android2.0以上则使用onstartCommand()方法。
    它们两个方法放在一起使用时，不会产生冲突。*//*

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {//在每次Service启动的时候调用
        Log.d("MyService","开启了服务")
        return super.onStartCommand(intent, flags, startId)
        */
/*通常状况下
        如果我们希望Service一旦启动就执行某个逻辑，那么这个逻辑就写在onStartCommand()里面*//*

    }

    override fun onDestroy() {//销毁Service时回收不再使用的资源
        Log.d("MyService","销毁了服务")
        super.onDestroy()
    }
}*/

class MyService :Service(){
    private val mBinder=DownloadBinder();

    inner class DownloadBinder:Binder(){
        fun startDownload(){
            Log.d("MyService","开始下载")
        }

        fun getProgress():Int{
            Log.d("MyService","下获取下载进度中")
            return 0
        }

        fun stopDownload(){
            Log.d("MyService","停止下载")
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onCreate() {//在Service创建的时候调用
        super.onCreate()
        Log.d("MyService","创建了服务")

        val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel=NotificationChannel("my_service","前台Service",NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val intent=Intent(this,MainActivity::class.java)
        val pi=PendingIntent.getActivity(this,1,intent,0)
        val notification=NotificationCompat.Builder(this,"my_service")
            .setContentIntent(pi)
            .setContentTitle("前台Service标题")
            .setContentText("前台Service内容")
            .setSmallIcon(R.drawable.small_icon)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.large_icon))
            .build()
        startForeground(1,notification)//开启一个前台
    }

    override fun onDestroy() {
        Log.d("MyService","销毁了服务")
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            //这种Service一旦启动，就会一直处于运行状态
            //必须调用stopService()或者stopSelf()或者被系统回收，Service才会停止
            //具体的逻辑
            Log.d("MyService","开启了服务")

            //如果想要Service执行完后自动停止
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }
}