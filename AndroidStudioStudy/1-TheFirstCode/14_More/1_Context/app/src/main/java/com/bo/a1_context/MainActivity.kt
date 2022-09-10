package com.bo.a1_context

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bo.a1_context.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val person=Person()
        person.name="Tome"
        person.age=21
        binding.button.setOnClickListener {
            var intent= Intent(this,FirstActivity::class.java)
            intent.putExtra("person_data",person)
            startActivity(intent)
        }
    }
}