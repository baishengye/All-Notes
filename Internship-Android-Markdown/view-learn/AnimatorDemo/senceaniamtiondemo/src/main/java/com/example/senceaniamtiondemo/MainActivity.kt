package com.example.senceaniamtiondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sceneRoot: ViewGroup = findViewById(R.id.scene_root)
        val aScene: Scene = Scene.getSceneForLayout(sceneRoot, R.layout.a_scene, this)
        val anotherScene: Scene = Scene.getSceneForLayout(sceneRoot, R.layout.another_scene, this)

        val inflateTransition =
            TransitionInflater.from(this)
                .inflateTransition(R.transition.fade_transition)

        val btn = findViewById<Button>(R.id.btn)
        var flag=true
        btn.setOnClickListener{
            flag = if(flag){
                TransitionManager.go(anotherScene,inflateTransition)
                false
            }else{
                TransitionManager.go(aScene,inflateTransition)
                true
            }
        }
    }
}