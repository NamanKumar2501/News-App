package com.example.newsassiment.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsassiment.R

class NewsDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        val intent = intent
        val headline = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val img = intent.getStringExtra("image")
        val src = intent.getStringExtra("source")

        // Forwarding to web_view
        val intent_web = Intent(this, NewsWebViewActivity::class.java)

        /*   // Image
           val imageView: ImageView = findViewById(R.id.image)
           Glide.with(this)
               .load(img)
               .into(imageView);
   */
        intent_web.putExtra("src", src)
        startActivity(intent_web)
        finish()


        /*  // Headline
          val textView: TextView = findViewById(R.id.headline)
          textView.text = headline

              intent_web.putExtra("src", src)
              startActivity(intent_web)
          finish()*/


        // Description
        /* val textViewDescription: TextView = findViewById(R.id.details)
         textViewDescription.text = description*/

    }
}