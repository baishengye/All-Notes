package com.bo.a1_learnandroidthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.bo.a1_learnandroidthread.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val updateText=1//updateText=1就标志这可以进行更新ViewText

    //注意handleMessage()是在主线程里面的
    private val handler=object:Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what){
                updateText->binding.textView.text="草泥马世界"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeTextBtn.setOnClickListener {
            /*thread { //在点击事件中开启了一个子线程，会报错Only the original thread that created a view hierarchy can touch its views
                //Android是不允许子线程去进行UI操作的
                binding.textView.text="草泥马世界"
            }*/

            /*但是Android提供了一种异步消息处理机制，完美的解决在子线程中进行UI操作的问题*/
            thread {
                val msg=Message()
                msg.what=updateText
                handler.sendMessage(msg)//把消息对象发送出去

                /*以上的流程:
                我们并没有直接在子线程中直接进行UI操作
                而是在子线程中创建了一个Message()消息对象,并将他的what属性指定为updateText
                然后调用Handle的sendMessage()方法将这条Message发送出去
                并且位于主线程中的handleMessage()方法接收到这条Message，判断要更改UI，就会更改了*/
            }
        }
    }
}