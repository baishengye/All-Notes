package com.bo.a1_counter.activity

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyObserver(val lifecycle: Lifecycle):LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart(){//活动开启的时候调用
        Log.d("MyObserver","活动开启")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityStop(){//活动停止的时候调用
        Log.d("MyObserver","活动停止")
    }

    /*@OnLifecycleEvent注解中对应生命周期的事件有6种
    ON_START
    ON_STOP
    ON_PAUSE
    ON_DESTORY
    ON_CREATE
    ON_RESUME
    还有一种 ON_ANY  表示匹配任何生命周期回调*/
}