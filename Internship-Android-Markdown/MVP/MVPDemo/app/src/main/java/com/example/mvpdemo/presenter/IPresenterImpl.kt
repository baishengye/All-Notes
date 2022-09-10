package com.example.mvpdemo.presenter

import com.example.mvpdemo.model.IModel
import com.example.mvpdemo.model.IModelImpl
import com.example.mvpdemo.view.IView

class IPresenterImpl() : IPresenter{
    private var iView:IView?=null
    private var iModel:IModel?=null

    public constructor(iView: IView) : this() {
        this.iView=iView
        iModel=IModelImpl(object : IModelImpl.Callback {
            override fun result(msg: String) {
                iView.updateView(msg)
            }
        })
    }

    override fun request() {
        //网络请求
        iModel?.request()
    }

    override fun detachView() {
        iView=null
    }

}