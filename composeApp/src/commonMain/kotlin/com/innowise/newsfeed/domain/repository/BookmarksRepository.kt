package com.innowise.newsfeed.domain.repository

import com.innowise.newsfeed.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface BookmarksRepository {
    fun getBookmarkedArticles(): Flow<List<Article>>

    fun getBookmarkedIds(): Flow<Set<Long>>

    suspend fun setBookmarked(articleId: Long, bookmarked: Boolean)
}
