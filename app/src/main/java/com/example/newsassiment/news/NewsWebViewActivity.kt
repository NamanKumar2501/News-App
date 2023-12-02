package com.example.newsassiment.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.example.newsassiment.R

class NewsWebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_web_view)

        val intent = intent
        val src = intent.getStringExtra("src")

        val web_view = findViewById<WebView>(R.id.web_view)
        if (src != null) {
            web_view.loadUrl(src)
        }

    }
}
