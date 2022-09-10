package com.bo.a1_counter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bo.a1_counter.activity.Repository
import com.bo.a1_counter.activity.User


class MainViewModel(countReserved:Int):ViewModel() {

    private val refreshLiveData=MutableLiveData<Any?>()

    val refreshResult=Transformations.switchMap(refreshLiveData){
        Repository.refresh()
    }

    fun refresh(){
        refreshLiveData.value=refreshLiveData.value
    }

    private val userLiveData=MutableLiveData<User>()
    //私有保证了userLiveData不会暴露出去

    val userName:LiveData<String> = Transformations.map(userLiveData){user->
        "${user.firstName} ${user.lastName}"
    }
    /*map()对LiveData的数据类型进行转换,接收两个参数:
    原始数据对象
    转换函数
    但原始数据userLiveData发生变化的时候,map()函数会监听到数据的变化并且把转换好的数据通知给userName的观察者*/

    private val userIdLiveData= MutableLiveData<String>()

    val user:LiveData<User> = Transformations.switchMap(userIdLiveData){userId->
        Repository.getUser(userId)
    }

    fun getUser(userId:String){
        userIdLiveData.value=userId
    }

    /*fun getUser(userId:String):LiveData<User>{
        return Repository.getUser(userId)
    }*/


    val counter: LiveData<Int>
        get() = _counter//外部只能通过get函数通过counter获取_counter，而不能设置直接设置_counter，这样就安全啦

    private val _counter=MutableLiveData<Int>()//此时可变的MutableLiveData是一个外部无法访问的
    init {
        _counter.value=countReserved//countReserved记录之前的计数值，创建ViewModel的时候把数据恢复
    }

    fun plusOne(){
        val count=_counter.value?:0
        _counter.value=count+1
    }

    fun clear(){
        _counter.value=0
    }
}


/*
class MainViewModel(countReserved:Int):ViewModel() {
    val counter=MutableLiveData<Int>()

    //MutableLiveData是一种可变的LiveData
    */
/*getValue()获取数据
    setValue()设定数据，只能在主线程中使用
    postValue()在非主线程中设定数据*//*


    init {
        counter.value=countReserved//countReserved记录之前的计数值，创建ViewModel的时候把数据恢复
    }

    fun plusOne(){
        val count=counter.value?:0
        counter.value=count+1
    }

    fun clear(){
        counter.value=0
    }
}*/
