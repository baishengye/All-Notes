package com.bo.a3_learnactivity_lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DialogActivity : AppCompatActivity() {
    //对话框式的Activity,但是仅仅这样还没有完成对话框的设置，还需要去AndroidManifest.xml配置
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialag_layout)
    }
}