package com.example.xmlanimatordemo

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.xmlanimatordemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnValueAnimator.setOnClickListener(this)
        binding.btnObjectAnimator.setOnClickListener(this)
        binding.btnAnimatorSet.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            binding.btnValueAnimator.id->{
                val valueAnimator = AnimatorInflater.loadAnimator(this,
                    R.animator.value_animator) as ValueAnimator
                valueAnimator.addUpdateListener { updatedAnimation ->
                    val animatedValue = updatedAnimation.animatedValue as Float
                    binding.btnValueAnimator.translationX = animatedValue
                }
                valueAnimator.start()
            }
            binding.btnObjectAnimator.id->{
                val objectAnimator = AnimatorInflater.loadAnimator(this, R.animator.object_animator) as ObjectAnimator
                objectAnimator.target=binding.btnObjectAnimator
                objectAnimator.start()
            }
            binding.btnAnimatorSet.id->{
                val set = AnimatorInflater.loadAnimator(this, R.animator.set_animator) as AnimatorSet
                set.setTarget(binding.btnAnimatorSet)
                set.start()
            }
        }
    }
}