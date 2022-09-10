package com.xiaoyingbo.messenger

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    var remoteMessage:Messenger ?= null/*远程信使*/
    var localMessenger:Messenger ?= null/*本地信使*/
    private var bound:Boolean?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById<Button>(R.id.btn_bind_service).setOnClickListener(this)
        findViewById<Button>(R.id.btn_send_msg).setOnClickListener(this)
    }

    private var connection:ServiceConnection = object :ServiceConnection {
        override fun onServiceConnected( name:ComponentName,  service:IBinder) {
            //从原始 IBinder 创建一个 Messenger，该 IBinder 之前已使用 getBinder 检索到。
            remoteMessage = Messenger(service);/*从服务端的IBinder中获取到远程信使*/
            bound = true;
        }

        override fun onServiceDisconnected( name:ComponentName) {
            bound = false;
        }
    };


    class ReturnHandler : Handler() {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun handleMessage(msg:Message) {
            when (msg.what) {
                MessengerService.MSG_SET_VALUE->{/*如果handler中接收到的消息时从服务端来的*/
                    Log.e("messenger-demo","Received from service: " + msg.arg1+ ""+Application.getProcessName());
                }
                else->{
                    super.handleMessage(msg)
                }
            }
        }
    }
    override fun onClick( v:View) {
        when (v.id) {
            R.id.btn_bind_service -> {/*开启服务*/
                bindService(Intent (this@MainActivity, MessengerService::class.java),
                    connection,
                    Context.BIND_AUTO_CREATE)/*绑定服务*/
            }
            R.id.btn_send_msg->{/*发送消息给服务端*/
                try {
                    localMessenger = Messenger(ReturnHandler())/*本地信使会从ReturnHandler这个Handler中收到服务端返回的消息*/

                    var msg = Message.obtain(null, MessengerService.MSG_REGISTER_CLIENT)/**从享元池中取出对应客户端发送的消息*/
                    msg.replyTo = localMessenger;/*先发送一条消息注册客户端的信使*/
                    //先发一则消息添加Messenger：msg.replyTo = localMessenger;
                    remoteMessage!!.send(msg);/*远程信使发送给消息*/

                    // Give it some value as an example.
                    msg = Message.obtain(null, MessengerService.MSG_SET_VALUE, this.hashCode(), 0);
                    //传入的arg1值:this.hashCode()
                    remoteMessage!!.send(msg);
                } catch ( e:RemoteException) {
                    e.printStackTrace()
                }
            }

        }
    }
    override fun onStop() {
        super.onStop();
        if (bound == true) {
            unbindService(connection);
            bound = false;
        }
    }
}