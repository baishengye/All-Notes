package com.bo.a3_retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {//单例类
    private const val BASE_URL="http://10.0.2.2/"//根路径

    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //基础写法
    fun <T> create(serviceClass: Class<T>):T= retrofit.create(serviceClass)

    //或者用内联函数来实化泛型
    inline fun <reified T> create(): T = create(T::class.java)
}