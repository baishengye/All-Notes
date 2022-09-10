package com.example.zoomanimationdemo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.example.zoomanimationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private var currentAnimator: Animator? = null

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private var shortAnimationDuration: Int = 0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hook up clicks on the thumbnail views.

        binding.thumbButton1.setOnClickListener {
            zoomImageFromThumb(binding.thumbButton1, R.mipmap.fate)
        }

        // Retrieve and cache the system's default "short" animation time.
        shortAnimationDuration = 5000
    }

    private fun zoomImageFromThumb(thumbView: View, imageResId: Int) {
        //如果有正在进行的动画，取消它
        //立即执行这个命令。
        currentAnimator?.cancel()

        //加载高分辨率的“放大”图片。
        binding.expandedImage.setImageResource(imageResId)

        //计算放大图像的起始和结束边界。
        //这一步涉及很多数学运算
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        //起始边界是缩略图的全局可见矩形，
        //和最后的边界是容器的全局可见矩形
        //View,还将容器视图的偏移量设置为
        // bounds，因为这是定位动画的原点
        // properties (X, Y)。
        thumbView.getGlobalVisibleRect(startBoundsInt)
        binding.root.getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        //调整起始边界为与最终相同的宽高比
        //使用“中心裁剪”技术进行边界。这可以防止不受欢迎的
        //在动画中拉伸。同时计算开始缩放
        // factor(最终缩放因子总是1.0)。
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            //扩展水平起始边界
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            //扩展垂直起始边界
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        //隐藏缩略图，显示放大的视图。
        //当动画开始时，它会将放大的视图定位到缩略图位置
        thumbView.alpha = 0f
        binding.expandedImage.visibility = View.VISIBLE

        //设置SCALE_X和SCALE_Y转换的枢轴点
        //显示在放大视图的左上角(默认是视图的中心)。
        binding.expandedImage.pivotX = 0f
        binding.expandedImage.pivotY = 0f
        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        currentAnimator = AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(binding.expandedImage, View.X, startBounds.left, finalBounds.left)
            ).apply {
                with(ObjectAnimator.ofFloat( binding.expandedImage, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat( binding.expandedImage, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat( binding.expandedImage, View.SCALE_Y, startScale, 1f))
                with(ObjectAnimator.ofFloat(binding.expandedImage,"alpha",0f,1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        binding.expandedImage.setOnClickListener {
            currentAnimator?.cancel()

            // Animate the four positioning/sizing properties in parallel,
            // back to their original values.
            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat( binding.expandedImage, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat( binding.expandedImage, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat( binding.expandedImage, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat( binding.expandedImage, View.SCALE_Y, startScale))
                }
                duration = shortAnimationDuration.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        thumbView.alpha = 1f
                        binding.expandedImage.visibility = View.GONE
                        currentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        thumbView.alpha = 1f
                        binding.expandedImage.visibility = View.GONE
                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }

}