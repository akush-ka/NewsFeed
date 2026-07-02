package com.innowise.newsfeed.data.repository

import com.innowise.newsfeed.data.local.dao.BookmarkDao
import com.innowise.newsfeed.data.local.entity.BookmarkEntity
import com.innowise.newsfeed.data.mapper.toDomain
import com.innowise.newsfeed.domain.model.Article
import com.innowise.newsfeed.domain.repository.BookmarksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarksRepositoryImpl(
    private val bookmarkDao: BookmarkDao,
) : BookmarksRepository {
    override fun getBookmarkedArticles(): Flow<List<Article>> =
        bookmarkDao.getBookmarkedArticles().map { entities -> entities.map { it.toDomain() } }

    override fun getBookmarkedIds(): Flow<Set<Long>> =
        bookmarkDao.getBookmarkedIds().map { it.toSet() }

    override suspend fun setBookmarked(articleId: Long, bookmarked: Boolean) {
        if (bookmarked) {
            bookmarkDao.insertBookmark(BookmarkEntity(articleId = articleId))
        } else {
            bookmarkDao.deleteBookmark(articleId)
        }
    }
}
