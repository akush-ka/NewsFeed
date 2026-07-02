package com.innowise.newsfeed.domain.repository

import com.innowise.newsfeed.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface LocalNewsRepository {
    fun getArticles(): Flow<List<Article>>

    suspend fun getArticle(articleId: Long): Article?

    suspend fun saveArticles(articles: List<Article>)
}
