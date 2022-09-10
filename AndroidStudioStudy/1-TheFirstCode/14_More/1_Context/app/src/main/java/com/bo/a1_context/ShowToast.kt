package com.bo.a1_context

import android.widget.Toast

fun String.showToast(duration: Int=Toast.LENGTH_SHORT){
    Toast.makeText(MyApplication.context,this,duration)
}