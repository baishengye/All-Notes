package com.example.propertyvaluesholderdemo

import android.animation.*
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import com.example.propertyvaluesholderdemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding:ActivityMainBinding
    private lateinit var valueAnimator:ValueAnimator

    private lateinit var animatorSet:AnimatorSet
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPropertyValuesHolder.post{
            val propertyX = PropertyValuesHolder.ofFloat("translationX",
                binding.btnPropertyValuesHolder.translationX,
                500f)
            val propertyY = PropertyValuesHolder.ofFloat("translationY",//距离:px
                binding.btnPropertyValuesHolder.translationX,
                500f)
            val propertyAlpha=PropertyValuesHolder.ofFloat("alpha",0f,1f)//透明度:0~1
            val propertyRotation=PropertyValuesHolder.ofFloat("rotation",0f,360f)//角度:度数制


            val array = Array(3){
                FloatArray(2)
            }
            array[0][0]=500f
            array[0][1]=300f
            array[1][0]=500f
            array[1][1]=1000f
            array[2][0]=500f
            array[2][1]=300f
            val propertyLayoutParams = PropertyValuesHolder.ofMultiFloat("layoutParams",array)


            val path = Path()
            path.quadTo(0f,0f,500f,1000f)
            path.quadTo(200f,200f,300f,100f)
            val propertyPath = PropertyValuesHolder.ofMultiFloat("path", path)

            val propertyTran = PropertyValuesHolder.ofMultiFloat("tran",
                TranConverter(Tran::class.java, FloatArray::class.java),
                TranEvaluator(),
                Tran(0f, 0f),
                Tran(600f, 1200f))


            val k0 = Keyframe.ofFloat(0.125f, 1f)
            val k1 = Keyframe.ofFloat(0.275f, 0.1f)
            val k2 = Keyframe.ofFloat(0.69f, 0.5f)
            val k3 = Keyframe.ofFloat(1f, 1f)
            val propertyKeyframe = PropertyValuesHolder.ofKeyframe("keyframe", k0, k1, k2, k3)


            //PropertyValuesHolder.ofFloat(Property.of())
            valueAnimator = ValueAnimator.ofPropertyValuesHolder(propertyX, propertyY,propertyAlpha,propertyRotation,propertyLayoutParams,propertyPath,propertyTran,propertyKeyframe)
            valueAnimator.apply {
                duration=5000
                repeatCount=0
                repeatMode=ValueAnimator.REVERSE
                addUpdateListener { animation->
                    /*val currX = animation.getAnimatedValue("translationX") as Float
                    val currY = animation.getAnimatedValue("translationY") as Float*/
                    val currAlpha=animation.getAnimatedValue("alpha") as Float
                    val currRotation=animation.getAnimatedValue("rotation") as Float
                    val currLayoutParams = animation.getAnimatedValue("layoutParams") as FloatArray
                    val currPath = animation.getAnimatedValue("path") as FloatArray
                    val currTran = animation.getAnimatedValue("tran") as FloatArray
                    val currKeyframe = animation.getAnimatedValue("keyframe") as Float

                    //binding.btnPropertyValuesHolder.translationX=currX
                    //binding.btnPropertyValuesHolder.translationY=currY
                    //binding.btnPropertyValuesHolder.alpha=currAlpha
                    binding.btnPropertyValuesHolder.apply {
                        rotationX=currRotation
                        rotationY=currRotation

                        val mLayoutParams = layoutParams
                        mLayoutParams.width= currLayoutParams[0].toInt()
                        mLayoutParams.height=currLayoutParams[1].toInt()
                        layoutParams=mLayoutParams

                        x=currTran[0]
                        y=currTran[1]
                        alpha=currKeyframe
                        /*x=currPath[0]
                        y=currPath[1]*/
                    }
                }
            }
        }

        animatorSet= AnimatorSet()

        binding.btnPropertyValuesHolder.setOnClickListener(this)

        animatorSet.addListener {
            object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.root.invalidate()
                }
            }
        }
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            binding.btnPropertyValuesHolder.id->{
                animatorSet.play(valueAnimator)
                animatorSet.start()
            }
        }
    }
}