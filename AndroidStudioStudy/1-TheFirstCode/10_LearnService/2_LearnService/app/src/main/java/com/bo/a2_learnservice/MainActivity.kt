package com.bo.a2_learnservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.bo.a2_learnservice.databinding.ActivityMainBinding

/*
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var downloadBinder:MyService.DownloadBinder

    private val connection=object:ServiceConnection{//只有在Activity和Service成功绑定的时候调用
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            downloadBinder=service as MyService.DownloadBinder//由IBinder向下转型变成MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        override fun onServiceDisconnected(name: ComponentName?) {//只有在服务创建进程奔溃或者被杀掉的时候才会被杀掉
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bindService.setOnClickListener {
            val intent=Intent(this,MyService::class.java)
            bindService(intent,connection,Context.BIND_AUTO_CREATE)
            //Context.BIND_AUTO_CREATE表示如果没有服务就自动创建一个服务，
            // 然后把this，也就是MainActivity和intent中指定的服务建立connection联系
            //此处会发现这样创建服务的时候只调用了onCreate(),并没有调用onStartCommand()
            //而且我们会发现这样创建的服务调用了只有先解绑了服务才能正常调用stopService(),正常销毁伏虎
        }

        binding.unbindService.setOnClickListener {
            downloadBinder.stopDownload()
            unbindService(connection)//解绑服务
        }

        binding.startService.setOnClickListener {
            val intent=Intent(this,MyService::class.java)
            startService(intent)
        }

        binding.stopService.setOnClickListener {
            var intent=Intent(this,MyService::class.java)
            stopService(intent)
        }
        */
/*这里开启服务和关闭服务都是定义在Context类中所以Activity可以用
        除了在Activity中关闭其实Service可以自行关闭:在Service中调用stopSelf()即可*//*

    }
}*/


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var downloadBinder:MyService.DownloadBinder

    private val connection=object:ServiceConnection{//只有在Activity和Service成功绑定的时候调用
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        downloadBinder=service as MyService.DownloadBinder//由IBinder向下转型变成MyService.DownloadBinder
        downloadBinder.startDownload()
        downloadBinder.getProgress()
    }

        override fun onServiceDisconnected(name: ComponentName?) {//只有在服务创建进程奔溃或者被杀掉的时候才会被杀掉
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bindService.setOnClickListener {
            val intent=Intent(this,MyService::class.java)
            bindService(intent,connection,Context.BIND_AUTO_CREATE)
            //Context.BIND_AUTO_CREATE表示如果没有服务就自动创建一个服务，
            // 然后把this，也就是MainActivity和intent中指定的服务建立connection联系
            //此处会发现这样创建服务的时候只调用了onCreate(),并没有调用onStartCommand()
            //而且我们会发现这样创建的服务调用了只有先解绑了服务才能正常调用stopService(),正常销毁伏虎
        }

        binding.unbindService.setOnClickListener {
            downloadBinder.stopDownload()
            unbindService(connection)//解绑服务
        }

        binding.startService.setOnClickListener {
            val intent=Intent(this,MyService::class.java)
            startService(intent)
        }

        binding.stopService.setOnClickListener {
            var intent=Intent(this,MyService::class.java)
            stopService(intent)
        }
        /*这里开启服务和关闭服务都是定义在Context类中所以Activity可以用
        除了在Activity中关闭其实Service可以自行关闭:在Service中调用stopSelf()即可*/

        binding.startIntentService.setOnClickListener {
            Log.d("MainActivity","当前现成的id是 ${Thread.currentThread().name}")
            var intent=Intent(this,MyIntentService::class.java)
            startService(intent)
        }
    }
}