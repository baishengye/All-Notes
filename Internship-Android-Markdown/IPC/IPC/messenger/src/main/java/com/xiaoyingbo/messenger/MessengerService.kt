package com.xiaoyingbo.messenger

import android.app.Application
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi


class MessengerService : Service() {
    private val TAG="messenger-demo"

    /** 用于显示和隐藏我们的通知。  */
    var mClients = ArrayList<Messenger>()

    /** 保存客户端设置的最后一个值。  */
    var mValue = 0

    //让客户端向IncomingHandler发送消息。
    var messenger: Messenger? = null

    //当绑定到服务时，我们向我们的Messenger返回一个接口，用于向服务发送消息。
    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG,"MessengerService-onBind")
        //创建 Messenger 对象（对 Handler 的引用）
        messenger = Messenger(IncomingHandler(this))
        //返回支持此Messenger的IBinder。
        return messenger!!.binder
    }

    //实现了一个 Handler
    internal inner class IncomingHandler(context: Context) : Handler() {
        private val applicationContext: Context
        init {
            applicationContext = context.applicationContext
        }
        @RequiresApi(Build.VERSION_CODES.P)
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_REGISTER_CLIENT -> mClients.add(msg.replyTo)
                MSG_UNREGISTER_CLIENT -> mClients.remove(msg.replyTo)
                MSG_SET_VALUE -> {
                    mValue = msg.arg1
                    var i: Int = mClients.size - 1
                    while (i >= 0) {
                        try {
                            Log.d("messenger-demo",mValue.toString()+" "+Application.getProcessName())
                            mClients[i].send(Message.obtain(null,MSG_SET_VALUE, mValue, 0))
                        } catch (e: RemoteException) {
                            // 客户端没了。 从列表中删除它;
                            //从后往前安全，从前往后遍历数组越界。
                            mClients.removeAt(i)
                        }
                        i--
                    }
                    super.handleMessage(msg)
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    companion object {
        /**
         * 数组中添加 Messenger (来自客户端)。
         * Message 的 replyTo 字段必须是应该发送回调的客户端的 Messenger。
         */
        const val MSG_REGISTER_CLIENT = 1

        /**
         * 数组中删除 Messenger (来自客户端)。
         * Message 的 replyTo 字段必须是之前用 MSG_REGISTER_CLIENT 给出的客户端的 Messenger。
         */
        const val MSG_UNREGISTER_CLIENT = 2

        /**
         * 用于设置新值。
         * 这可以发送到服务以提供新值，并将由服务发送给具有新值的任何注册客户端。
         */
        const val MSG_SET_VALUE = 3
    }
}