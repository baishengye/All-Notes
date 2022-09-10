package com.example.motionlayoutdemo2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val faButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        faButton.setOnClickListener {
            Toast.makeText(this,"floatingActionButton",Toast.LENGTH_SHORT).show()
        }
    }
}