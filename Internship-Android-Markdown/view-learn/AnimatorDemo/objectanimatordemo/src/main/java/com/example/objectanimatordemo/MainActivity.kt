package com.example.objectanimatordemo

import android.animation.*
import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import com.example.objectanimatordemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var animatorSet: AnimatorSet

    private lateinit var objectAnimator1:ObjectAnimator
    private lateinit var objectAnimator2:ObjectAnimator
    private lateinit var objectAnimator3:ObjectAnimator

    @SuppressLint("ObjectAnimatorBinding")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnObjectAnimator.setOnClickListener(this)

        objectAnimator1 = ObjectAnimator.ofFloat(binding.btnObjectAnimator, "translationX", 0f,600f)
        objectAnimator1.apply {
            duration=3000
            repeatMode=ObjectAnimator.REVERSE
            repeatCount=0

            interpolator=AnticipateInterpolator()
        }

        val propertyTranY = PropertyValuesHolder.ofFloat("translationY", 0f, 200f, 500f, 700f, 200f, 400f, 0f)
        val propertyRotation = PropertyValuesHolder.ofFloat("rotation", 0f, 360f)


        /*val array = Array(3){
            FloatArray(2)
        }
        array[0][0]=500f
        array[0][1]=300f
        array[1][0]=500f
        array[1][1]=1000f
        array[2][0]=500f
        array[2][1]=300f
        val size = PropertyValuesHolder.ofMultiFloat("size",array)*/


        objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(
            binding.btnObjectAnimator,
            propertyTranY,
            //size,
            propertyRotation
        )
        objectAnimator2.apply {
            duration=3000
            repeatMode=ObjectAnimator.REVERSE
            repeatCount=0

            /*addUpdateListener { animator->
                val currLayoutParams = animator.getAnimatedValue("size") as FloatArray
                layoutParams.width=currLayoutParams[0].toInt()
                layoutParams.height=currLayoutParams[1].toInt()
                binding.btnObjectAnimator.layoutParams=layoutParams
            }*/

            interpolator=LinearInterpolator()
        }

        animatorSet= AnimatorSet()

        animatorSet.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                Toast.makeText(this@MainActivity,"onAnimationEnd",Toast.LENGTH_SHORT).show()
            }
        })

        animatorSet.apply {
            //interpolator= AccelerateDecelerateInterpolator()//该插值器的变化率在开始和结束时缓慢但在中间会加快
            //interpolator=AccelerateInterpolator()//该插值器的变化率在开始时较为缓慢，然后会加快。
            //interpolator=AnticipateInterpolator()	//该插值器先反向变化，然后再急速正向变化。
            //interpolator=AnticipateOvershootInterpolator()	//该插值器先反向变化，再急速正向变化，然后超过定位值，最后返回到最终值。
            //interpolator=BounceInterpolator()	//该插值器的变化会跳过结尾处。
            //interpolator=CycleInterpolator(2f)	//该插值器的动画会在指定数量的周期内重复。
            //interpolator=DecelerateInterpolator()	//该插值器的变化率开始很快，然后减速。
            //interpolator=LinearInterpolator()	//该插值器的变化率恒定不变。
            //interpolator=OvershootInterpolator()	//该插值器会急速正向变化，再超出最终值，然后返回。
            /*setInterpolator (
                object : TimeInterpolator{
                    override fun getInterpolation(input: Float): Float {
                        var i = input*2f
                        return i*2f
                    }
                }
            )*/	//该接口用于实现您自己的插值器。
        }
        animatorSet.addListener(
            object : AnimatorListenerAdapter() {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    Log.d("mainAnimator", animatorSet.totalDuration.toString())
                }
            }
        )

        val layoutParams1 = ConstraintLayout.LayoutParams(500, 300)
        val layoutParams2 = ConstraintLayout.LayoutParams(200, 1000)
        val layoutParams3 = ConstraintLayout.LayoutParams(400, 200)
        objectAnimator3 = ObjectAnimator.ofObject(binding.btnObjectAnimator,"layoutParams",
            object : TypeEvaluator<ViewGroup.LayoutParams>{
                override fun evaluate(
                    fraction: Float,
                    startValue: ViewGroup.LayoutParams?,
                    endValue: ViewGroup.LayoutParams?
                ): ViewGroup.LayoutParams {
                    val layoutParams = ViewGroup.LayoutParams(0, 0)
                    layoutParams.width =
                        ((endValue!!.width - startValue!!.width) * fraction + startValue.width).toInt()
                    layoutParams.height =
                        ((endValue.height - startValue.height) * fraction + startValue.height).toInt()
                    return layoutParams
                }
            },layoutParams1,layoutParams2,layoutParams3)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            binding.btnObjectAnimator.id->{
                animatorSet.apply {
                    play(objectAnimator3)
                        .with(objectAnimator2)
                        .with(objectAnimator1)
                    start()
                }
            }
        }
    }
    //当objectAnimator和所在的animatorSet都设置了interpolator,动画会使用animatorSet的interpolator
    //duration等属性都是优先使用animatorSet,animatorSet没有设置的属性才会使用objectAnimator
    //同一个animatorSet中的objectAnimator的duration等属性都是各用各的
    //animatorSet中设置duration等属性是设置给每个objectAnimator,而不是给整个animatorSet
}