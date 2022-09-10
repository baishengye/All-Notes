package com.bo.a1_learnnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.bo.a1_learnnotification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //获取viewBinding来操作布局
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //得到通知管理器
        val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            /*//设置一个通知渠道
            //渠道Id,渠道名称,重要级别
            val channel= NotificationChannel("normal","Normal",IMPORTANCE_DEFAULT)

            //通过通知管理器创建一个通知起到
            manager.createNotificationChannel(channel)
            //系统只会创建一次通知渠道，下次再执行代码系统就会检测到通知渠道，然后不创建*/


            val channel2=NotificationChannel("important","Important", IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel2)
        }

        binding.sendNotice.setOnClickListener {
            /*val intent=Intent(this,NotificationActivity::class.java)
            val pi=PendingIntent.getActivity(this,0, intent,0)
            val notification= NotificationCompat.Builder(this,"normal")
                .setContentTitle("通知的标题")
                .setContentText("通知的内容")
                .setSmallIcon(R.drawable.small_icon)//通知的小图标
                .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.large_icon))//通知的大图标
                .setContentIntent(pi)//设置通知的意图内容
                .setAutoCancel(false)//设置通知是不是点击完了之后就自动关闭
                .setStyle(NotificationCompat.BigTextStyle().bigText("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"))
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources,R.drawable.big_image)))
                .build()
            //显示通知,每个通知指定的id必须都不同
            manager.notify(1,notification)*/

            val intent1=Intent(this,NotificationActivity::class.java)
            val pi1=PendingIntent.getActivity(this,1,intent1,0)
            val notification1=NotificationCompat.Builder(this,"important")
                .setContentTitle("通知的标题")
                .setContentText("通知的内容")
                .setSmallIcon(R.drawable.small_icon)//通知的小图标
                .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.large_icon))//通知的大图标
                .setContentIntent(pi1)//设置通知的意图内容
                .setAutoCancel(false)//设置通知是不是点击完了之后就自动关闭
                .setStyle(NotificationCompat.BigTextStyle().bigText("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"))
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources,R.drawable.big_image)))
                .build()
            manager.notify(2,notification1)
        }

    }
}