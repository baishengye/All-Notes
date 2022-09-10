package com.bo.a7_learnpersistence_1_learnfile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputText=load()
        if(inputText.isNotEmpty()){
            editText.setText(inputText)
           /* 不太明白为什么这里不能用editText.text=inputText*/
            Log.d("MainActivity","inputText=${inputText}")

            editText.setSelection(inputText.length)//这个是把输入光标移动到文本的末尾
            Toast.makeText(this,"加载数据成功",Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        /*确保在程序销毁前会把数据保存到储存设备里面*/
        val inputText=editText.text.toString()
        save(inputText)
    }

    private fun load(): String {
        val content=StringBuilder()
        try {
            val input=openFileInput("data")
            val reader=BufferedReader(InputStreamReader(input))
            reader.forEachLine { //输入流读取文件的每一行回调到lambda表达式
                content.append(it)
            }
        }catch (e:IOException){
            e.printStackTrace()
        }
        return content.toString()
    }

    private fun save(inputText: String) {
        try {
            /*通过openFileOutput打开一个文件输出流，得到一个FileOutStream对象*/
            val output=openFileOutput("data",Context.MODE_PRIVATE)
            /*接着使用FileOutStream对象构建出一个BufferedWriter对象*/
            val write=BufferedWriter(OutputStreamWriter(output))
            /*用BufferedWriter对象把文字写到文件里面*/
            write.use {
                /*use函数是kotlin提供的一个内置扩展函数，他确保表达式中的代码全部执行完了之后自动将外层的流关闭
                这样就不需要自己手动去关闭流了*/
                it.write(inputText)
            }
        }catch (e:IOException){//异常处理
            e.printStackTrace()
        }
    }
}
