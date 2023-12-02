package com.example.newsassiment.news.model

import androidx.annotation.StringRes

data class News(
    @StringRes val stringResourceId: Int,
    val imageUrl: Int,
    val description: Int,
    val source: Int,
)