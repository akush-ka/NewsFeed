package com.innowise.newsfeed.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String?,
    val url: String?,
    val coverImageUrl: String?,
    val publishedTimestamp: String?,
    val readingTimeMinutes: Int?,
    val authorName: String?,
    val tags: String,
)
