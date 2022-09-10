package com.bo.a1_context

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MyApplication:Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context//但是静态变量很容易造成内存泄漏
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
        //这个获取到的Context是Application的，全局只会存在一份实例,
        //并且在整个应用程序的生命周期内不会回收，所以上面的静态变量是不会内存泄漏的
    }
}