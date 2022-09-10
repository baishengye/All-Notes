package com.example.layoutanimationsdemo

import android.animation.Animator
import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.layoutanimationsdemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    private val list= listOf<Int>(R.drawable.baseline_accessible_forward_red_300_24dp,
    R.drawable.baseline_accessibility_red_300_24dp,
    R.drawable.baseline_accessible_red_300_24dp)

    private var i=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLayoutAnimationAdd.setOnClickListener(this)
        binding.btnLayoutAnimationRemove.setOnClickListener(this)

        initAnim()
    }

    private fun getInAnim(): Animator? {
        val trX = PropertyValuesHolder.ofFloat("translationX", 100f, 0f)
        val trY = PropertyValuesHolder.ofFloat("translationY", 0f, 0f)
        val trAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
        return ObjectAnimator.ofPropertyValuesHolder(binding.ll,trX,trY,trAlpha)
    }

    private fun getOutAnim(): Animator? {
        val trY = PropertyValuesHolder.ofFloat("translationY", 0f, 100f)
        val trX = PropertyValuesHolder.ofFloat("translationX", 0f, 0f)
        val trAlpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f)
        return ObjectAnimator.ofPropertyValuesHolder(binding.ll,trY, trAlpha, trX)
    }

    private fun initAnim() {
        val mTransition = LayoutTransition()
        mTransition.setDuration(LayoutTransition.CHANGE_APPEARING, 2000)
        mTransition.setDuration(LayoutTransition.CHANGE_DISAPPEARING, 2000)
        mTransition.setDuration(LayoutTransition.APPEARING, 2000)
        mTransition.setDuration(LayoutTransition.DISAPPEARING, 2000)
        //-----------------------设置动画--------------------
        mTransition.setAnimator(LayoutTransition.APPEARING, getInAnim())
        mTransition.setAnimator(LayoutTransition.DISAPPEARING, getOutAnim())
        //---------------------------------------------------
        mTransition.setStartDelay(LayoutTransition.CHANGE_APPEARING, 50)
        mTransition.setStartDelay(LayoutTransition.APPEARING, 50)
        mTransition.setStartDelay(LayoutTransition.DISAPPEARING,0)
        mTransition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 50)
        //----viewgroup绑定----
        binding.ll.layoutTransition = mTransition
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            binding.btnLayoutAnimationAdd.id->{
                addView();
            }
            binding.btnLayoutAnimationRemove.id->{
                removeView();
            }
        }
    }

    private fun removeView() {
        binding.ll.removeViewAt(i)
        i--;
    }

    private fun addView() {
        i++;
        val idx=i%3
        val imageView = ImageView(this)
        imageView.setImageResource(list[idx])
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        binding.ll.addView(imageView, i, params)

    }


}