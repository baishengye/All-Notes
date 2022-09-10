package com.example.springforceanimationdemo

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.SeekBar
import androidx.dynamicanimation.animation.SpringAnimation


class MainActivity : Activity() {
    //XY坐标
    private var downX = 0f
    private var downY = 0f

    //调整参数的SeekBar
    private var dampingSeekBar: SeekBar? = null
    private var stiffnessSeekBar: SeekBar? = null

    //X/Y方向速度相关的帮助类
    private var velocityTracker: VelocityTracker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.content).systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        stiffnessSeekBar = findViewById<View>(R.id.stiffness) as SeekBar
        dampingSeekBar = findViewById<View>(R.id.damping) as SeekBar
        velocityTracker = VelocityTracker.obtain()
        val box: View = findViewById(R.id.box)
        findViewById<View>(R.id.content).setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downX = event.x
                        downY = event.y
                        velocityTracker!!.addMovement(event)
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        box.translationX = event.x - downX
                        box.translationY = event.y - downY
                        velocityTracker!!.addMovement(event)
                        return true
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        velocityTracker!!.computeCurrentVelocity(1000)
                        if (box.translationX !in -0.00001..0.00001) {
                            val animX = SpringAnimation(box, SpringAnimation.TRANSLATION_X,0f)
                            animX.spring.stiffness = getStiffnessSeekBar()
                            animX.spring.dampingRatio = getDampingSeekBar()
                            animX.setStartVelocity(velocityTracker!!.xVelocity)
                            animX.start()
                        }
                        if (box.translationY - 0f !in -0.00001..0.00001){
                            val animY = SpringAnimation(box, SpringAnimation.TRANSLATION_Y, 0f)
                            animY.spring.stiffness = getStiffnessSeekBar()
                            animY.spring.dampingRatio = getDampingSeekBar()
                            animY.setStartVelocity(velocityTracker!!.yVelocity)
                            animY.start()
                        }
                        velocityTracker!!.clear()
                        return true
                    }
                }
                return false
            }
        })
    }

    /**
     * 从SeekBar获取自定义的强度
     *
     * @return 强度float
     */
    private fun getStiffnessSeekBar(): Float {
        return stiffnessSeekBar!!.progress.toFloat().coerceAtLeast(1f)
    }

    /**
     * 从SeekBar获取自定义的阻尼
     *
     * @return 阻尼float
     */
    private fun getDampingSeekBar(): Float {
        return dampingSeekBar!!.progress / 100f
    }
}