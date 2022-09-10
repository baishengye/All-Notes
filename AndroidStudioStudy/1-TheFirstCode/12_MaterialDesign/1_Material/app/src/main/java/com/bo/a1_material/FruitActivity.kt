package com.bo.a1_material

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bo.a1_material.databinding.ActivityFruitBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class FruitActivity:AppCompatActivity() {
    companion object{
        const val FRUIT_NAME="fruit_name"
        const val FRUIT_IMAGE_ID="fruit_image_id"
    }
    private lateinit var binding: ActivityFruitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFruitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fruitName=intent.getStringExtra(FRUIT_NAME)?:""
        val fruitImageId=intent.getIntExtra(FRUIT_IMAGE_ID,0)
        setSupportActionBar(binding.toolbar)//设置标题栏是toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//显示导航按钮
        binding.collapsingToolbar.title=fruitName//标题栏的标题
        Glide.with(this).load(fruitImageId).into(binding.fruitImageView)
        //通过上下文加载图片到对应上下文(视图)的对应图片视图里面
        binding.fruitContentText.text=generateFruitContent(fruitName)//设置屏幕中心卡片中的文字内容

        binding.floatingBtn.setOnClickListener {
            Snackbar.make(it,"点击了悬浮按钮",Snackbar.LENGTH_SHORT)
                .setAction("我看到了") {
                    Toast.makeText(this, "看的好", Toast.LENGTH_SHORT).show()
                }.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {//标题栏(包括菜单中)中的按钮按下之后都会回调这个函数
        when(item.itemId){
            android.R.id.home->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun generateFruitContent(fruitName:String)=fruitName.repeat(500)
}