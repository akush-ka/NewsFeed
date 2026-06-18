package com.innowise.newsfeed.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.innowise.newsfeed.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Upsert
    suspend fun upsertArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articles")
    fun getArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getArticleById(id: Long): ArticleEntity?
}
