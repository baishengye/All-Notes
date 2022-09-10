package com.bo.a1_material

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun <T>T.showToast(context: Context, duration: Int= Toast.LENGTH_SHORT){
    Toast.makeText(context,this.toString(),duration).show()
}

fun <T:String>View.showSnackbar(text:String, action:T?=null,duration: Int=Snackbar.LENGTH_SHORT,
                      block: (() -> Unit)? =null){
    val snackbar=Snackbar.make(this,text,duration)
    if(action!=null&&block!=null){
        snackbar.setAction(action.toString()){
            block()
        }
    }
    snackbar.show()
}