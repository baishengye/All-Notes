package com.bo.a1_context

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        /*val person=intent.getSerializableExtra("person_data") as Person*/
        val person=intent.getParcelableExtra<Person>("person_data") as Person
        //虽然这里的person和MainActivity中的person的数据完全一样，但是是两个不同的对象,就像是克隆人和本体一样
        Log.d("FirstActivity","person is ${person.toString()}")
    }
}