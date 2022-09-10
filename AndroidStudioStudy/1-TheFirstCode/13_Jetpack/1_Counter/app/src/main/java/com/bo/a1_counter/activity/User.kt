package com.bo.a1_counter.activity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(var firstName:String,var lastName:String,var age:Int) {
    @PrimaryKey(autoGenerate = true)//id是从0开始自增的主键
    var id:Long=0
}