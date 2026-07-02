package com.innowise.newsfeed.data.mapper

import com.innowise.newsfeed.data.local.entity.ArticleEntity
import com.innowise.newsfeed.domain.model.Article

fun ArticleEntity.toDomain(): Article = Article(
    id = id,
    title = title,
    description = description.orEmpty(),
    url = url.orEmpty(),
    coverImageUrl = coverImageUrl.orEmpty(),
    publishedTimestamp = publishedTimestamp.orEmpty(),
    readingTimeMinutes = readingTimeMinutes ?: 0,
    authorName = authorName.orEmpty(),
    tags = tags,
)

fun Article.toEntity(): ArticleEntity = ArticleEntity(
    id = id,
    title = title,
    description = description,
    url = url,
    coverImageUrl = coverImageUrl,
    publishedTimestamp = publishedTimestamp,
    readingTimeMinutes = readingTimeMinutes,
    authorName = authorName,
    tags = tags,
)
