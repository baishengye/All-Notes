package com.example.constraintlayoutdemo

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintProperties
import androidx.constraintlayout.widget.ConstraintSet
import com.example.constraintlayoutdemo.databinding.Test4Binding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: Test4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=Test4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val constraintProperties = ConstraintProperties(binding.AndroidImg)

        binding.AndroidImg.setOnClickListener {
            Toast.makeText(this,"AndroidImg",Toast.LENGTH_SHORT).show()
            constraintProperties.alpha(0f).apply()
        }
    }
}