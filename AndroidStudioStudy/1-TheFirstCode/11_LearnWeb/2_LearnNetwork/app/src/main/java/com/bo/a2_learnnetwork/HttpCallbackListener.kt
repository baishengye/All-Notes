package com.bo.a2_learnnetwork

import java.lang.Exception

interface HttpCallbackListener {
    fun onFinish(response: String)//服务器成功响应请求的时候调用,response是成功返回的数据
    fun onError(e:Exception)//服务器失败响应请求的时候调用,e中是失败的信息
}