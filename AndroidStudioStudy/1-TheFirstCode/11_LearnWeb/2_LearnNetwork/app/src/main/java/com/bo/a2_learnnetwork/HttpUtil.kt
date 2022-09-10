package com.bo.a2_learnnetwork

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object HttpUtil {
    fun sendOkHttpRequest(address:String,callback: okhttp3.Callback){//callback其实也是一个接口
        val client=OkHttpClient()
        val request=Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(callback)
    //okhttp3会在enqueue这个方法中帮我们开好子线程，然后在子线程中发送请求，并将结果回调到okHttp.CallBack中
    }

    fun sendHttpRequest(address:String, listener: HttpCallbackListener){
        thread {
            var connection:HttpURLConnection?=null
            try {
                val response=StringBuilder()
                //把返回的信息存到一个StringBuilder中(因为要多次拼接，单线程下超快的，也没有安全问题)
                val url=URL(address)//把字符串解析成URL对象
                connection=url.openConnection() as HttpURLConnection
                connection.connectTimeout=8000
                connection.readTimeout=8000
                val input=connection.inputStream
                val reader=BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                listener.onFinish(response.toString())
            }catch (e:Exception){
                e.printStackTrace()
                listener.onError(e)
            }finally {
                connection?.disconnect()
            }
        }
    }
}