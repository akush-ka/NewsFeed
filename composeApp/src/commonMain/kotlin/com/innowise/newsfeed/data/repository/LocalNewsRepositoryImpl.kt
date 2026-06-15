package com.innowise.newsfeed.data.repository

import com.innowise.newsfeed.data.local.dao.ArticleDao
import com.innowise.newsfeed.data.local.entity.ArticleEntity
import com.innowise.newsfeed.domain.repository.LocalNewsRepository
import kotlinx.coroutines.flow.Flow

class LocalNewsRepositoryImpl(
    private val articleDao: ArticleDao,
) : LocalNewsRepository {
    override fun getArticles(): Flow<List<ArticleEntity>> =
        articleDao.getArticles()

    override suspend fun getArticle(articleId: Long): ArticleEntity? =
        articleDao.getArticleById(articleId)

    override suspend fun saveArticles(articles: List<ArticleEntity>) =
        articleDao.upsertArticles(articles)
}
