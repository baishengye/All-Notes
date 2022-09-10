package com.bo.a3_retrofit

import retrofit2.Call
import retrofit2.http.GET

interface AppService {
    @GET("get_data.json")//这条注解是在注解下面这个方法
    fun getAppData(): Call<List<App>>
    //当调用这个方法的时候Retrofit发起了一条GET请求，请求的地址就是括号里的相对地址
    //方法的返回值必须声明成Retrofit中的内置的Call参数，并通过泛型来指定数要转化成什么对象
}