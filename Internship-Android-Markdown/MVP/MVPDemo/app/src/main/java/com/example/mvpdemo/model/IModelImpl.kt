package com.example.mvpdemo.model

class IModelImpl(private val callback: Callback) : IModel {

    public interface Callback{
        fun result(msg:String)
    }

    override fun request() {
        callback.result("new")
    }
}