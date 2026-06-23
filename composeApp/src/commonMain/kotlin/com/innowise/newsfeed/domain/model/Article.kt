package com.innowise.newsfeed.domain.model

data class Article(
    val id: Long,
    val title: String,
    val description: String,
    val url: String,
    val coverImageUrl: String,
    val publishedTimestamp: String,
    val readingTimeMinutes: Int,
    val authorName: String,
    val tags: String,
)
