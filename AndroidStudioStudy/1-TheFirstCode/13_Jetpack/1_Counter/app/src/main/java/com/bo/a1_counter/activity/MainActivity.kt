package com.bo.a1_counter.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.System.putInt
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.bo.a1_counter.R
import com.bo.a1_counter.databinding.ActivityMainBinding
import com.bo.a1_counter.viewModel.MainViewModel
import com.bo.a1_counter.viewModel.MainViewModelFactory
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.doWorkBtn.setOnClickListener {
            val request=OneTimeWorkRequest.Builder(SimpleWorker::class.java)
                .setInitialDelay(5,TimeUnit.MINUTES)//延迟5分钟
                .addTag("simple")//添加一个标签(我们可以通过标签来取消后台任务)
                .setBackoffCriteria(BackoffPolicy.LINEAR,10,TimeUnit.MINUTES)//如果任务失败就重启任务Result.retry()
                    //指定三个参数
                    //第一个参数是用于指定如果任务再次执行失败，下次的时间以什么方式延迟
                            // LINEAR:下次重试的时间以线性的方式延迟
                            //EXPONENTIAL:下次重试的时间以指数的方式延迟
                    //第二个参数和第三个参数指延迟的时间:10分钟
                .build()
            WorkManager.getInstance(this).enqueue(request)//enqueue:这个任务实际是在子线程中运行的
            WorkManager.getInstance(this).cancelAllWorkByTag("simple")//标签来取消后台任务
            WorkManager.getInstance(this).cancelWorkById(request.id)//根据id来取消后台任务
            WorkManager.getInstance(this).cancelAllWork()//取消所有后台任务
            WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(request.id)//根据id来监听,还可以根据标签监听(getWorkInfoByTagLiveData)
                .observe(this){workInfo->//监听后台任务的执行结果
                    if(workInfo.state==WorkInfo.State.SUCCEEDED){
                        Log.d("MainActivity","任务执行成功")
                    }else{
                        Log.d("MainActivity","任务执行失败")
                    }
                }

        }

        lifecycle.addObserver(MyObserver(lifecycle))

        //这里通过SharedPreferences储存数据
        sp=getPreferences(Context.MODE_PRIVATE)
        val countReserved=sp.getInt("count_reserved",0)

        viewModel=ViewModelProviders.of(this,MainViewModelFactory(countReserved)).get(MainViewModel::class.java)
        //这个地方一定不能用viewModel=MainViewModel()来创造一个viewModel的实例
        //因为如果这样创建的话ViewModel就是在Activity这个活动的线程中，也就是会和Activity同生共死了
        //当屏幕反转时Activity被销毁时viewModel也会被销毁，Activity创建的时候会创建一个新的viewModel实例
        //而ViewModelProviders.of(this).get(MainViewModel::class.java)获得的viewModel实例是不用依托Activity的
        //这样就把数据保存下来了
        binding.counterBtn.setOnClickListener {
            /*viewModel.counter++*/
            viewModel.plusOne()
            refreshCounter()
        }

        binding.clearBtn.setOnClickListener {
            /*viewModel.counter=0*/
            viewModel.clear()
            refreshCounter()
        }

        binding.getUser.setOnClickListener {
            var userId=(0..10000).random().toString()
            viewModel.getUser(userId)
        }

        viewModel.user.observe(this){user->
            binding.infoText.text=user.firstName
        }

        /*viewModel.counter.observe(this, Observer{count->
            binding.infoText.text=count.toString()
        })*/
        /*此处this是livecycleOwner是一个单抽象方法接口，observe也是单抽象方法接口
        当一个java同时接收两个单抽象方法接口参数的时候，要么都是用函数式API，要么都不使用
        由于这里传入的是一个this，不是函数式使用的，所以Observer也必须不是函数式使用*/
        /*但是这是在kotlin中，Google还是有了一些拓展来使用函数式API的*/
        viewModel.counter.observe(this){count->
            binding.infoText.text=count.toString()
        }
        refreshCounter()




        var userDao=AppDatabase.getDatabase(this).userDao()
        val user1=User("章","北海",49)
        val user2=User("罗","辑",20)
        //数据库操作都是很耗时的，Room是不允许数据库操作出现在主线程中的，所以要把操作放在子线程中
        binding.addDataBtn.setOnClickListener {
            thread {
                user1.id=userDao.insertUser(user1)
                user2.id=userDao.insertUser(user2)
            }
        }

        binding.updateData.setOnClickListener {
            thread {
                user1.age=42
                userDao.updateUser(user1)
            }
        }

        binding.deleteData.setOnClickListener {
            thread {
                userDao.deleteUserByLastName("辑")
            }
        }

        binding.getDataBtn.setOnClickListener {
            thread {
                for (user in userDao.loadAllUsers()){
                    Log.d("MainActivity",user.toString())
                }
            }
        }













    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved",viewModel.counter.value?:0)
        }
    }

    private fun refreshCounter(){
        binding.infoText.text=viewModel.counter.toString()//刷新UI上的数据
    }
}