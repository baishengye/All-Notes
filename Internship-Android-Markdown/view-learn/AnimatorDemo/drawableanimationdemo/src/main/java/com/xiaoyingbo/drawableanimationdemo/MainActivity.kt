package com.xiaoyingbo.drawableanimationdemo

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.xiaoyingbo.drawableanimationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawableAnimation: AnimationDrawable
    private lateinit var vectorDrawableAnimation: AnimatedVectorDrawable
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.ivDrawableAnimation.apply {
            setBackgroundResource(R.drawable.animation_drawable)
            drawableAnimation = background as AnimationDrawable
       }

       binding.ivVectorDrawableAnimation.apply {
           setBackgroundResource(R.drawable.animation_vector_drawable)
           vectorDrawableAnimation=background as AnimatedVectorDrawable
       }

        binding.ivDrawableAnimation.setOnClickListener { drawableAnimation.start() }
        binding.ivVectorDrawableAnimation.setOnClickListener { vectorDrawableAnimation.start() }
    }

}