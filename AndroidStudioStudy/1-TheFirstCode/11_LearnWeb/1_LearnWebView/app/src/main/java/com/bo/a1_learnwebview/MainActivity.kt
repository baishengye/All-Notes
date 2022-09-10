package com.bo.a1_learnwebview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.bo.a1_learnwebview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.settings.javaScriptEnabled=true
        binding.webView.webViewClient=WebViewClient()//表示当需要从一个网页跳转到另一个网页的时候，我们需要目标网页是在webView中打开也不是打开系统浏览器
        binding.webView.loadUrl("https://www.bilibili.com")//在webView中加载一个Uri网址字符串的对应网页的内容,
    }
}