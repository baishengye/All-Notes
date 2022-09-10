package com.bo.a7_learnpreferences_2_learnsharedpreferences

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveButton.setOnClickListener {
            /*var editor= getSharedPreferences("data", Context.MODE_PRIVATE).edit()
            editor.putString("name","波")
            editor.putInt("age",21)
            editor.putBoolean("married",false)
            editor.apply()//用apply()完成提交*/

            getSharedPreferences("data",Context.MODE_PRIVATE).open {
                putString("name", "波")
                putInt("age", 21)
                putBoolean("married", false)
            }
            Toast.makeText(this,"123456 ",Toast.LENGTH_LONG).show()
        }

        loadButton.setOnClickListener {
            getSharedPreferences("data", Context.MODE_PRIVATE).apply {
                val name=getString("name","")
                val age=getInt("age",0)
                val marries=getBoolean("marries",false)
                Log.d("MainActivity", "${name}今年${age},婚姻状况是${marries} ")
            }

        }
    }
}