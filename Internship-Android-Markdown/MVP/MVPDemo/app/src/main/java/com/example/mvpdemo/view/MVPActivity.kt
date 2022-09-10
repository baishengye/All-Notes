package com.example.mvpdemo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mvpdemo.R
import com.example.mvpdemo.presenter.IPresenter
import com.example.mvpdemo.presenter.IPresenterImpl

class MVPActivity : AppCompatActivity(),IView {
    private lateinit var bt:Button
    private var iPresenter: IPresenter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp)

        bt=findViewById<Button>(R.id.bt)

        iPresenter=IPresenterImpl(this)

        bt.setOnClickListener {
            iPresenter?.request()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        iPresenter?.detachView()
        iPresenter=null
    }

    override fun updateView(msg: String) {
        bt.text=if (msg == "new"){
            "old"
        }else{
            "new"
        }
    }
}