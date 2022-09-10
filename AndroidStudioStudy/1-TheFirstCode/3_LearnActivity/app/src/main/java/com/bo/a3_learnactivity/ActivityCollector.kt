package com.bo.a3_learnactivity

import android.app.Activity

object ActivityCollector {
    private val activitis = ArrayList<Activity>()

    fun addActivity(activity: Activity){
        activitis.add(activity)
    }

    fun removeActivity(activity: Activity){
        activitis.remove(activity)
    }

    fun finishAll(){
        for (activity in activitis)
            if(!activity.isFinishing){
                activity.finish()
            }

        activitis.clear()
    }
}