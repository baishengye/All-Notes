package com.xiaoyingbo.binder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var iTestBinder:ITestInterface

    private val connect:ServiceConnection=object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e("aidl","onServiceConnected success");
            iTestBinder=ITestInterface.Stub.asInterface(service);
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            //TODO("Not yet implemented")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent()
        /*intent.setPackage("com.xiaoyingbo.server");
        intent.setClassName("com.xiaoyingbo.server","AIDLService")*/
        intent.component = ComponentName("com.xiaoyingbo.server","com.xiaoyingbo.server.AIDLService")
        bindService(intent,connect,Context.BIND_AUTO_CREATE)

        findViewById<TextView>(R.id.tv).setOnClickListener{
            iTestBinder.test()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unbindService(connect)
    }
}