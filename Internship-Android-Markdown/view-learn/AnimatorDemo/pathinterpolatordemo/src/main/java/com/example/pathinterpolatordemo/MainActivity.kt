package com.example.pathinterpolatordemo

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.PathInterpolator
import androidx.core.view.animation.PathInterpolatorCompat
import com.example.pathinterpolatordemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.path1.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val path = Path().apply {
                    arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true)
                }
                val animator = ObjectAnimator.ofFloat(binding.path1, View.X, View.Y, path).apply {
                    duration = 2000
                    start()
                }
            }
        }

        binding.path2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val path = Path().apply {
                    arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true)
                }
                val animator = ObjectAnimator.ofFloat(binding.path2, View.X, View.Y, path).apply {
                    duration = 2000
                    interpolator=AnimationUtils.loadInterpolator(this@MainActivity,R.anim.test_path_interpolator)
                    start()
                }
            }
        }
    }
}