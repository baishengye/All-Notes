package com.bo.a4_learnplayvideo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bo.a4_learnplayvideo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //通过解析路径的方式获取到文件的Uri对象
        val uri= Uri.parse("android.resource://$packageName/${R.raw.video}")
        binding.videoView.setVideoURI(uri)//通过Uri对象把视频设置到viewView空间中去

        binding.play.setOnClickListener {
            if(!binding.videoView.isPlaying)
                binding.videoView.start()
        }

        binding.pause.setOnClickListener {
            if(binding.videoView.isPlaying)
                binding.videoView.pause()
        }

        binding.stop.setOnClickListener {
            if(binding.videoView.isPlaying){
                binding.videoView.resume()//重新开始播放
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.suspend()//把videoView占用的资源释放掉
    }
}