package com.bo.a6_learnbroadcast_broaddemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val prefs=getPreferences(Context.MODE_PRIVATE)
        val isRemember=prefs.getBoolean("remember_password",false)
        if(isRemember){
            val account=prefs.getString("account","bi")
            val password=prefs.getString("password","12345")
            accountEdit.setText(account)
            if (account != null) {
                accountEdit.setSelection(account.length)
            }
            passwordEdit.setText(password)
            if (password != null) {
                passwordEdit.setSelection(password.length)
            }
            rememberPass.isChecked=true
        }

        //登录逻辑，登陆成功就进入MainActivity
        login.setOnClickListener {
            val account=accountEdit.text.toString()
            val password=passwordEdit.text.toString()

            //账号:bo,密码:123456，就成功登录
            if(account=="bo"&&password=="123456"){
                val editor=prefs.edit()
                if(rememberPass.isChecked){
                    editor.putBoolean("remember_password",rememberPass.isChecked)
                    editor.putString("account",account)
                    editor.putString("password",password)
                }else{
                    editor.clear()//如果用户不想记住账户和密码就直接把存储数据清空
                }
                editor.apply()


                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"账号或密码错误", Toast.LENGTH_LONG).show()
            }

        }
    }
}