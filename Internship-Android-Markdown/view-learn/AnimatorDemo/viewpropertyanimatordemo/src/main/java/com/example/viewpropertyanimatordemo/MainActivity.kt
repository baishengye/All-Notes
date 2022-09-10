package com.example.viewpropertyanimatordemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.viewpropertyanimatordemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnViewPropertyAnimator.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            binding.btnViewPropertyAnimator.id->{
                binding.btnViewPropertyAnimator.animate().apply {
                    rotationX(360f)
                    rotationY(360f)
                    rotation(360f)
                    alpha(0f).alpha(1f)
                    x(500f).x(200f)
                    y(1200f).y(200f)
                    duration = 5000
                    start()

                }
            }
        }
    }
}