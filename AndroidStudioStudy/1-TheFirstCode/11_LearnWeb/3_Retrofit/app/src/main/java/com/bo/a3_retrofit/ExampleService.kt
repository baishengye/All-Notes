package com.bo.a3_retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/*interface ExampleService {
    @GET("get_data.json")
    fun getData():Call<Data>
}*/

/*
在很多场景下路径是不断动态变化的
        比如:http://example.com/<page>/get_data.json
在这个接口中<page>页数是不断变化的
        那么应该怎么写呢?*/

/*
interface ExampleService {
    @GET("{page}/get_data.json")//这样就指定了page是一个变量，可以当作参数传递过来
    fun getData(@Path("page") page:Int):Call<Data>
}*/

/*或者还会传递一系列的参数
比如:http://example.com/get_data.json?u=<user>&t=<token> */

/*interface ExampleService{
    @GET("get_data.son")
    fun getData(@Query("u") user:String,@Query("t") token:String):Call<Data>
    *//*@Query注解对u和t声明，那么Retrofit在就会自动按参数GET请求的格式将这两个参数构建到请求地址当中*//*
}*/

/*DELETE http://example.com/data/<id>*/
/*interface ExampleService{
    @DELETE("data/{id}")
    fun createData(@Path("id") id: String):Call<ResponseBody>
    *//*ResponseBody表示能够接收任意类型的数据*//*
}*/


/*POST http://example.com/data/create
{"id":1,"content":"123456"}
interface ExampleService{
    @POST("data/create")
    fun createData(@Body data: Data):Call<ResponseBody>
    ResponseBody表示能够接收任意类型的数据
}*/


/*有些服务器接口可能还要求我们在http请求的header中指定参数
        比如
        GET http://example.com/get_data.json
        User-Agent:okHttp
        Cache-Control:max-age=0
interface ExampleService{
    @Header("User-Agent":"okHttp","Cache-control":"max-age=0")
    fun createData():Call<Data>
}*/

/*有些服务器接口可能还要求我们在http请求的header中指定参数
比如
GET http://example.com/get_data.json
User-Agent:okHttp
Cache-Control:max-age=0
interface ExampleService{
    @GET("get_data.json")
    fun getData(
        @Header("User-Agent") user-Agent:String,
    @Header("Cache-Control" cache-Control:String)
    ):Call<Data>
}*/
