package com.example.flinganimationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import com.example.flinganimationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var v=1000f
        binding.btn.setOnClickListener{
            Toast.makeText(this,"bind",Toast.LENGTH_SHORT).show()
            FlingAnimation(binding.btn, DynamicAnimation.TRANSLATION_Y).apply {
                v = if(v==1000f){
                    setStartVelocity(v)
                    -1000f
                }else{
                    setStartVelocity(v)
                    1000f
                }

                setMinValue(0f)
                setMaxValue(10000f)
                friction = 0.2f
                start()
            }
        }
    }
}
