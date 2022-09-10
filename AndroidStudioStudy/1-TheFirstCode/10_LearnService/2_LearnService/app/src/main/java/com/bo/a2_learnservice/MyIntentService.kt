package com.bo.a2_learnservice

import android.app.IntentService
import android.content.Intent
import android.util.Log

/*这里必须要先调用父类的构造函数，传入的字符串没有意义，只为调试用，啥字符串都行*/
class MyIntentService:IntentService("MyIntentService") {//由于IntentService的特性Service在运行完后会自动停止
    override fun onHandleIntent(p0: Intent?) {
        //打印当前线程的id
        Log.d("MyIntentService","当前现成的id是 ${Thread.currentThread().name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyIntentService","销毁线程")
    }
}