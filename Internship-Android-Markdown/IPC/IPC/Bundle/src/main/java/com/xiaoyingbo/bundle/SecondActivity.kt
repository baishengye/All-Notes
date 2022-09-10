package com.xiaoyingbo.bundle

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    private var mybinder: MyService.MyBinder? = null
    private var conn:ServiceConnection= object :ServiceConnection {
        override fun onServiceConnected(name:ComponentName , service:IBinder ) {
            Log.e("bundleDemo","执行了onServiceConnected(),当前Acitivity与服务连接成功");
        }

        override fun onServiceDisconnected(name:ComponentName ) {
            Log.e("bundleDemo","执行了onServiceDisconnected(),当前Acitivity与服务断开连接");
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        Log.e("bundleDemo","onCreate")

        bindService(Intent(this@SecondActivity,MyService::class.java), conn, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()

        unbindService(conn)
    }
}

class MyService:Service(){
    //1. 自定义一个内部类MyBinder类继承Binder,,从而实现IBinder接口
    class MyBinder : Binder() {
        fun getService():MyService {
            return MyService();   //返回当前Service类
        }
    }

    //2. 实例化 一个MyBinder 对象
    private var mybinder:MyBinder  = MyBinder();

    override fun onBind(intent:Intent):IBinder {
        // TODO: Return the communication channel to the service.
        Log.i("bundleDemo","执行了onBind(),服务绑定成功");
        return mybinder;
    }



    override fun  onUnbind( intent:Intent):Boolean {
        Log.e("bundleDemo","执行了onUnbind(),服务解绑成功");
        return super.onUnbind(intent);
    }

    override fun onCreate() {
        Log.e("bundleDemo","执行了onCreste(),服务创建成功");
        super.onCreate();
    }

    override fun  onStartCommand(intent:Intent , flags:Int , startId:Int):Int {
        Log.e("bundleDemo","执行了onStartCommand(),服务启动成功");
        return super.onStartCommand(intent, flags, startId);
    }

    override fun  onDestroy() {
        Log.e("bundleDemo","执行了onDestroy(),服务被销");
        super.onDestroy();
    }
}