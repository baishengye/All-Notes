package com.example.viewanimationutilsdemo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var flag:Boolean=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn)

        btn.setOnClickListener{
            if(flag){
                hide()
                flag=false
            }else if (!flag){
                show()
                flag=true
            }
        }
    }

    fun show(){
        // previously invisible view
        val myView: View = findViewById(R.id.my_view)

        // Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            val cx = myView.width / 2
            val cy = myView.height / 2

            // get the final radius for the clipping circle
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            // create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, finalRadius)
            // make the view visible and start the animation
            myView.visibility = View.VISIBLE
            anim.start()
        } else {
            // set the view to invisible without a circular reveal animation below Lollipop
            myView.visibility = View.INVISIBLE
        }
    }

    fun hide(){
        // previously visible view
        val myView: View = findViewById(R.id.my_view)

        // Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            val cx = myView.width / 2
            val cy = myView.height / 2

            // get the initial radius for the clipping circle
            val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            // create the animator (the final radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0f)

            // make the view invisible when the animation is done
            anim.addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    myView.visibility = View.INVISIBLE
                }
            })

            // start the animation
            anim.start()
        } else {
            // set the view to visible without a circular reveal animation below Lollipop
            myView.visibility = View.VISIBLE
        }
    }
}