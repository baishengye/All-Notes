package com.bo.a3_learnactivity_lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NormalActivity : AppCompatActivity() {//普通的Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.normal_layout)
    }
}