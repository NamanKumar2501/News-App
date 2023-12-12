package com.example.newsassiment.news.networking

data class NewsApi(
    val totalResults: Int,
    val articles: List<Article>
)