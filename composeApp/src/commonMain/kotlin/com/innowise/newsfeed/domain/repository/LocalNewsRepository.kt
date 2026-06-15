package com.innowise.newsfeed.domain.repository

import com.innowise.newsfeed.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface LocalNewsRepository {
    fun getArticles(): Flow<List<ArticleEntity>>

    suspend fun getArticle(articleId: Long): ArticleEntity?

    suspend fun saveArticles(articles: List<ArticleEntity>)
}
