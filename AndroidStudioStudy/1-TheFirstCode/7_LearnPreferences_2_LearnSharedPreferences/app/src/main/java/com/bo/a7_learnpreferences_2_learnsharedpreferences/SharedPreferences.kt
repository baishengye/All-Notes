package com.bo.a7_learnpreferences_2_learnsharedpreferences

import android.content.Context
import android.content.SharedPreferences

fun SharedPreferences.open(block:SharedPreferences.Editor.()->Unit){
    val editor=edit()//open拥有了SharedPreferences的上下文,就可以直接调用edit()
    editor.block()
    editor.apply()//提交数据
}
fun SharedPreferences.otherOpen(block: SharedPreferences.() -> Unit){
    block()
}
