package com.bo.a4_learnui_6_uidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val msgList=ArrayList<Msg>()

    private lateinit var adapter:MsgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMsg()
        val layoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        if(!::adapter.isInitialized)
            adapter=MsgAdapter(msgList)
        recyclerView.adapter= adapter
        send.setOnClickListener {
            val content=inputText.text.toString()
            if(content.isNotEmpty()){
                val msg=Msg(content,Msg.TYPE_RECEIVED)
                msgList.add(msg)
                adapter.notifyItemInserted(msgList.size-1)//有消息的时候就刷新recycleView中的显示
                recyclerView.scrollToPosition(msgList.size-1)//将recycleView定位到最后一行
                inputText.setText("")//清空输入框中的内容
            }
        }

    }

    private fun initMsg(){
        val msg1=Msg("你好",Msg.TYPE_RECEIVED)
        msgList.add(msg1)

        val msg2=Msg("你也好",Msg.TYPE_SENT)
        msgList.add(msg2)

        val msg3=Msg("我不好",Msg.TYPE_RECEIVED)
        msgList.add(msg3)
    }
}