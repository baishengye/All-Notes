package com.xiaoyingbo.bundle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.xiaoyingbo.bundle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startActivityForResult(Intent(this,SecondActivity::class.java),0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==0&&resultCode== RESULT_OK){
            Log.d("bundleDemo",data?.getIntExtra("INT",0).toString())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}