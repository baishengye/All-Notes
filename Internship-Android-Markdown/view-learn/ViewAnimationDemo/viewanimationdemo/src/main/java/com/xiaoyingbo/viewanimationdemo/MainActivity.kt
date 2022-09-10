package com.xiaoyingbo.viewanimationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.*
import com.xiaoyingbo.viewanimationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.btn.setOnClickListener {
            val viewAnim = AnimationUtils.loadAnimation(this, R.anim.animation_test)
            binding.btn.startAnimation(viewAnim)
        }*/

        val rotateAnimation = RotateAnimation(0f, 360f)
        val alphaAnimation = AlphaAnimation(0f, 1f)
        val translateAnimation = TranslateAnimation(0f, 0f, 100f, 100f)
        val scaleAnimation = ScaleAnimation(0f, 2f, 0f, 1f)
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(rotateAnimation)
        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(translateAnimation)
        animationSet.addAnimation(scaleAnimation)
        animationSet.duration=4000
        animationSet.repeatCount=2
        animationSet.repeatMode=Animation.REVERSE

        binding.btn.setOnClickListener {
            it.startAnimation(animationSet)
        }
    }
}