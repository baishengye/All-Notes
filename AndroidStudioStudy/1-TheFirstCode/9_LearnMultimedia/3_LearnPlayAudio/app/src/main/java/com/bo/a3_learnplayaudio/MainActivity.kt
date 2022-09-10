package com.bo.a3_learnplayaudio

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bo.a3_learnplayaudio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private val mediaPlayer=MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMediaPlayer()

        binding.play.setOnClickListener {
            if(!mediaPlayer.isPlaying)
                mediaPlayer.start()
        }

        binding.pause.setOnClickListener {
            if(mediaPlayer.isPlaying)
                mediaPlayer.pause()
        }

        binding.stop.setOnClickListener {
            if(mediaPlayer.isPlaying){
                mediaPlayer.reset()//重置mediaPlayer，重置完了之后需要重新初始化mediaPlayer
                initMediaPlayer()
            }
        }
    }

    private fun initMediaPlayer(){
        /*放在assets中的文件是可以直接加载的到assets管理器中的*/
        val assetsManager=assets
        /*调用openfd()方法打开句柄
                反正就跟把柄一个意思,掌握了你的把柄,你就难逃法网(系统),(系统)要找到你就可以根据句柄来找你*/
        val fd=assetsManager.openFd("music.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
        mediaPlayer.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()//停止mediaPlayer的功能
        mediaPlayer.release()//释放mediaPlayer的相关资源
    }
}