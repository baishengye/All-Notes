package com.xiaoyingbo.okhttp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.*
import okio.IOException
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        val request = Request.Builder().url("https://www.baidu.com")
            .get()
            .build()
        okHttpClient.newCall(request).enqueue(cb())
    }


    class cb:Callback{
        override fun onFailure(call: Call, e: IOException) {
            Log.e("okhttps",e.toString())
        }

        override fun onResponse(call: Call, response: Response) {
            Log.e("okhttps",response.toString())
        }

    }
}