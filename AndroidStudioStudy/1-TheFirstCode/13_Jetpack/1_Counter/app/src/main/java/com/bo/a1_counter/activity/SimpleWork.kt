package com.bo.a1_counter.activity

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleWorker(context: Context,params:WorkerParameters):Worker(context,params) {
    override fun doWork(): Result {//doWork不会在主线程中运行
        Log.d("SimpleWorker" ,"SimpleWorker开始工作")
        return Result.success()//返回逻辑执行的结果
    }
}