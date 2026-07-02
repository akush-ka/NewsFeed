package com.innowise.newsfeed.data.repository

import com.innowise.newsfeed.data.local.dao.ArticleDao
import com.innowise.newsfeed.data.mapper.toDomain
import com.innowise.newsfeed.data.mapper.toEntity
import com.innowise.newsfeed.domain.model.Article
import com.innowise.newsfeed.domain.repository.LocalNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalNewsRepositoryImpl(
    private val articleDao: ArticleDao,
) : LocalNewsRepository {
    override fun getArticles(): Flow<List<Article>> =
        articleDao.getArticles().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getArticle(articleId: Long): Article? =
        articleDao.getArticleById(articleId)?.toDomain()

    override suspend fun saveArticles(articles: List<Article>) =
        articleDao.upsertArticles(articles.map(Article::toEntity))
}
