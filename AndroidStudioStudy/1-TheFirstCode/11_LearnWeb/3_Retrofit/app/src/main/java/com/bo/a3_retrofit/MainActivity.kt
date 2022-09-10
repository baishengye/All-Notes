package com.bo.a3_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bo.a3_retrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getAppDataBtn.setOnClickListener {
            /*val retrofit= Retrofit.Builder()//构建一个Retrofit对象
                .baseUrl("http://10.0.2.2/")//设置根路径
                .addConverterFactory(GsonConverterFactory.create())//指定解析数据时的转换库
                .build()
            val appService=retrofit.create(AppService::class.java)//创建AppService这个接口的动态代理对象*/
            val appService=ServiceCreator.create<AppService>()
            appService.getAppData().enqueue(object : Callback<List<App>> {//重写Callback回调
            /*enqueue会开启一个子线程，当数据回调到CallBack中之后又自动切回主线程*/
            override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
                val list=response.body()
                if(list!=null){
                    for (app in list){
                        Log.d("MainActivity","id is ${app.id}")
                        Log.d("MainActivity","name is ${app.name}")
                        Log.d("MainActivity","version is ${app.version}")
                    }
                }
            }

                override fun onFailure(call: Call<List<App>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }
}