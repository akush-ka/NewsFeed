package com.innowise.newsfeed.data.mapper

import com.innowise.newsfeed.data.network.dto.ArticleDto
import com.innowise.newsfeed.data.network.dto.TagDto
import com.innowise.newsfeed.domain.model.Article
import com.innowise.newsfeed.domain.model.Tag

internal fun ArticleDto.toDomain(): Article = Article(
    id = id,
    title = title,
    description = description.orEmpty(),
    url = url.orEmpty(),
    coverImageUrl = (coverImage ?: socialImage).orEmpty(),
    publishedTimestamp = publishedTimestamp.orEmpty(),
    readingTimeMinutes = readingTimeMinutes ?: 0,
    authorName = (user?.name ?: organization?.name).orEmpty(),
    tags = tagList,
)

internal fun TagDto.toDomain(): Tag = Tag(
    id = id,
    name = name,
)
