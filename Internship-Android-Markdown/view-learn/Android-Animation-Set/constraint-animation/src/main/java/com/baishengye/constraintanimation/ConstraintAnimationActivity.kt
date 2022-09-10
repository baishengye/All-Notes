package com.baishengye.constraintanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.OvershootInterpolator
import android.widget.ImageView

class ConstraintAnimationActivity : AppCompatActivity() {
    private var selectedView: View? = null
    private lateinit var root: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_constraint_animation)

        selectedView = null
        val left=findViewById<ImageView>(R.id.left)
        val middle=findViewById<ImageView>(R.id.middle)
        val right=findViewById<ImageView>(R.id.right)

        left.setOnClickListener {
            if (selectedView != left) {
                updateConstraints(R.layout.activity_main_left)
                selectedView = left
            } else {
                toDefault()
            }
        }
        middle.setOnClickListener {
            if (selectedView != middle) {
                updateConstraints(R.layout.activity_main_middle)
                selectedView = middle
            } else {
                toDefault()
            }
        }

        right.setOnClickListener {
            if (selectedView != right) {
                updateConstraints(R.layout.activity_main_right)
                selectedView = right
            } else {
                toDefault()
            }
        }

        root = findViewById<ConstraintLayout>(R.id.root)
        root.setOnClickListener {
            toDefault()
        }
    }

    private fun toDefault() {
        if (selectedView != null) {
            updateConstraints(R.layout.activity_constraint_animation)
            selectedView = null
        }
    }

    private fun updateConstraints(@LayoutRes id: Int) {
        val newConstraintSet = ConstraintSet()
        newConstraintSet.clone(this, id)
        newConstraintSet.applyTo(root)
        val transition = ChangeBounds()
        transition.interpolator = OvershootInterpolator()
        TransitionManager.beginDelayedTransition(root, transition)
    }
}