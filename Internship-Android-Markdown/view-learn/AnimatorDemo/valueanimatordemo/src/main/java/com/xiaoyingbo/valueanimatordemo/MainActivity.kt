package com.xiaoyingbo.valueanimatordemo

import android.animation.*
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.xiaoyingbo.valueanimatordemo.databinding.ActivityMainBinding
import com.xiaoyingbo.valueanimatordemo.util.DensityUtil


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var valueAnimator1: ValueAnimator//rotation旋转
    private lateinit var valueAnimator2: ValueAnimator//x移动
    private lateinit var valueAnimator3: ValueAnimator//Y移动
    private lateinit var valueAnimator4: ValueAnimator//scale放大
    private lateinit var valueAnimator5: ValueAnimator//透明度
    private lateinit var valueAnimator6: ValueAnimator//布局参数控制width和height
    private lateinit var valueAnimator7: ValueAnimator//自定义估值器TypeEvaluator控制X和Y
    private lateinit var valueAnimator8: ValueAnimator//color变化
    private lateinit var valueAnimator9: ValueAnimator//PropertyValuesHolder控制
    private lateinit var animatorSet: AnimatorSet



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        valueAnimator1 = ValueAnimator.ofFloat(0f, 360f)
        //valueAnimator1.setStartDelay(1000);
        valueAnimator1.duration=1000
        valueAnimator1.repeatCount=0
        valueAnimator1.repeatMode=ValueAnimator.REVERSE
        valueAnimator1.addUpdateListener(AnimatorUpdateListener { animation ->
            val rotation = animation.animatedValue as Float
            binding.btnValueAnimator.rotation=rotation
            binding.btnValueAnimator.invalidate()
        })
        /*valueAnimator2 =
            ValueAnimator.ofFloat(binding.btnValueAnimator.x, 300f) //单位是像素
        //valueAnimator2.setStartDelay(1000);
        valueAnimator2.duration=1000
        valueAnimator2.repeatCount=0
        valueAnimator2.repeatMode=ValueAnimator.REVERSE
        valueAnimator2.addUpdateListener(AnimatorUpdateListener { animation ->
            val tranX = animation.animatedValue as Float
            binding.btnValueAnimator.x=tranX
        })

        valueAnimator3 = ValueAnimator.ofFloat(binding.btnValueAnimator.y,300f) //单位是像素
        //valueAnimator3.setStartDelay(1000);
        valueAnimator3.duration=1000
        valueAnimator3.repeatCount=0
        valueAnimator3.repeatMode=ValueAnimator.REVERSE
        valueAnimator3.addUpdateListener(AnimatorUpdateListener { animation ->
            val tranY = animation.animatedValue as Float
            binding.btnValueAnimator.y=tranY
        })*/

        /*valueAnimator4 = ValueAnimator.ofFloat(1f,3f) //单位是像素
        //valueAnimator4.setStartDelay(1000);
        valueAnimator4.duration=1000
        valueAnimator4.repeatCount=0
        valueAnimator4.repeatMode=ValueAnimator.REVERSE
        valueAnimator4.addUpdateListener(AnimatorUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            binding.btnValueAnimator.scaleX=scale
            binding.btnValueAnimator.scaleY=scale
            binding.btnValueAnimator.invalidate()
        })*/


        valueAnimator5 = ValueAnimator.ofFloat(1f,0f) //单位是像素
        //valueAnimator5.setStartDelay(1000);
        valueAnimator5.duration=1000
        valueAnimator5.repeatCount=0
        valueAnimator5.repeatMode=ValueAnimator.REVERSE
        valueAnimator5.addUpdateListener(AnimatorUpdateListener { animation ->
            val alpha=animation.animatedValue as Float
            binding.btnValueAnimator.alpha=alpha
        })

        //在onCreate或者onResume中不能获取view 的宽高是因为：
        // view的measure过程和activity的生命周期不是同步执行的，
        // 因此无法保证执行onCreate，onStart，onResume时某个view已经测量完毕
        binding.btnValueAnimator.post {
            val startWidth = binding.btnValueAnimator.width.toFloat()
            valueAnimator6 = ValueAnimator.ofFloat(startWidth, startWidth * 2)
            valueAnimator6.apply {
                duration=1000
                repeatCount=0
                repeatMode=ValueAnimator.REVERSE
                addUpdateListener { animation->
                    val layoutParams = binding.btnValueAnimator.layoutParams
                    layoutParams.apply {
                        val width = animation.animatedValue as Float
                        layoutParams.height=width.toInt()
                        layoutParams.width=width.toInt()
                    }
                    binding.btnValueAnimator.layoutParams=layoutParams
                }
            }
        }

        binding.btnValueAnimator.post{
            val x = binding.btnValueAnimator.x
            valueAnimator7 = ValueAnimator.ofObject(TypeEv(),
                Tran(binding.btnValueAnimator.x, binding.btnValueAnimator.y),
                Tran(binding.btnValueAnimator.x*5 , binding.btnValueAnimator.y*5 ))
            valueAnimator7.apply {
                duration=1000
                repeatCount=0
                repeatMode=ValueAnimator.REVERSE
                addUpdateListener { animation->
                    val animatedValue = animation.animatedValue as Tran
                    binding.btnValueAnimator.x=animatedValue.x
                    binding.btnValueAnimator.y=animatedValue.y
                }
            }
        }

        valueAnimator8 = ValueAnimator.ofArgb(
            Color.parseColor("#ff0000"),
            Color.parseColor("#0000ff"),
            Color.parseColor("#ff0000")
        )
        valueAnimator8.apply {
            duration=1000
            repeatCount=0
            repeatMode=ValueAnimator.REVERSE
            addUpdateListener { animation->
                val animatedValue = animation.animatedValue as Int
                binding.btnValueAnimator.setBackgroundColor(animatedValue)
            }
        }

        valueAnimator9 = ValueAnimator.ofPropertyValuesHolder(
            PropertyValuesHolder.ofFloat(
                "alpha",
                0f,
                1f,
                0f,
                1f
            )
        )
        valueAnimator9.apply {
            duration=1000
            repeatCount=0
            repeatMode=ValueAnimator.REVERSE
        }
        valueAnimator9.addUpdateListener {
            val animatedValue = it.getAnimatedValue("alpha") as Float
            binding.btnValueAnimator.alpha= animatedValue
        }


        animatorSet = AnimatorSet()
        binding.btnValueAnimator.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

           binding.btnValueAnimator.id -> {
               animatorSet.play(valueAnimator1)
                   //.with(valueAnimator2)
                   //.with(valueAnimator3)
                   //.with(valueAnimator4)
                   //.after(valueAnimator8)
                   .with(valueAnimator7)
                   .after(valueAnimator9)
                   .with(valueAnimator6)
               animatorSet.start()
           }
        }
    }
}

//问题:
//ofInt(@NonNull Property<Object, Integer> property, @NonNull int[] values)如何使用
//