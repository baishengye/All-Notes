---
title: 精美的聊天界面(Layout实战)
author: Bob
top: true
toc: true
mathjax: true
categories: Android学习
tags:
  - 第一行代码
  - Layout
abbrlink: 
summary: 《第一行代码》的RecyclerView学习
date: 2022-02-12 00:00:00
---

# 精美的聊天界面
1. 编辑mainActivity.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#d8e0e8">

    <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:id="@+id/recyclerView"
        android:layout_weight="1"/>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <EditText
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:id="@+id/inputText"
            android:layout_weight="1"
            android:hint="type text"
            android:maxLines="2"/>

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/send"
            android:text="Send"/>
    </LinearLayout>

</LinearLayout>
```
2. 编辑接收信息框msg_left_item.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="10dp">

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="left"
        android:background="@drawable/message_left_original">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/msgLeft"
            android:layout_gravity="center"
            android:layout_margin="10dp"
            android:textColor="#fff"/>

    </LinearLayout>

</FrameLayout>
```
3. 编辑发送出去了的信息框msg_right_item.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="10dp">

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="right"
        android:background="@drawable/message_right_original">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/msgRight"
            android:layout_gravity="center"
            android:layout_margin="10dp"
            android:textColor="#000"/>

    </LinearLayout>

</FrameLayout>
```
4. 编辑信息实例类Msg.kt
```kotlin
class Msg (val content:String, val type:Int){
    companion object{
        const val TYPE_RECEIVED=0
        const val TYPE_SENT=1
    }
}
```
5. 编辑Msg的适配器MsgAdapter.kt
```kotlin
class MsgAdapter(private val msgList: List<Msg>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    inner class LeftViewHolder(view:View):RecyclerView.ViewHolder(view){
        val leftMsg:TextView=view.findViewById(R.id.msgLeft)
    }
    inner class RightViewHolder(view: View):RecyclerView.ViewHolder(view){
        val rightMsg:TextView=view.findViewById(R.id.msgRight)
    }

    override fun getItemViewType(position: Int): Int {
        val msg=msgList[position]
        return msg.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        if(viewType==Msg.TYPE_RECEIVED){
            val view=LayoutInflater.from(parent.context).inflate(R.layout.msg_left_item,parent,false)
            LeftViewHolder(view)
        }else{
            val view=LayoutInflater.from(parent.context).inflate(R.layout.msg_right_item,parent,false)
            RightViewHolder(view)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = msgList[position]
        when(holder){
            is LeftViewHolder->holder.leftMsg.text=msg.content
            is RightViewHolder->holder.rightMsg.text = msg.content
            else-> throw IllegalAccessException()
        }
    }

    override fun getItemCount()=msgList.size
}
```
6. 编辑主活动MainActivity.xml
```kotlin
class MainActivity : AppCompatActivity() {

    private val msgList=ArrayList<Msg>()

    private var adapter:MsgAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMsg()
        val layoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        adapter=MsgAdapter(msgList)
        recyclerView.adapter= adapter
        send.setOnClickListener {
            val content=inputText.text.toString()
            if(content.isNotEmpty()){
                val msg=Msg(content,Msg.TYPE_RECEIVED)
                msgList.add(msg)
                adapter?.notifyItemInserted(msgList.size-1)//有消息的时候就刷新recycleView中的显示
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
```
   