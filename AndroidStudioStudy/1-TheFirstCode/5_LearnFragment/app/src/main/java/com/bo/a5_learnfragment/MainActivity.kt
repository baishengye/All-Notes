package com.bo.a5_learnfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.left_fragment.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*button.setOnClickListener {//给左侧Fragment中的按钮注册一个点击事件
            replaceFragment(AnotherRightFragment())//然后通过replaceFragment()动态添加RightFragment
        }
        replaceFragment(RightFragment())*/
    }

    /*private fun replaceFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager//获取fragmentManager
        val transaction=fragmentManager.beginTransaction()//开启一个事务
        transaction.replace(R.id.rightLayout,fragment)//向容器中添加或者替换Fragment,replace(容器的id,待添加的Fragmnet)
        transaction.addToBackStack(null)
        transaction.commit()
    }*/
}