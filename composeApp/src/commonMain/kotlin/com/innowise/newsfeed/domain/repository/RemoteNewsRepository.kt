package com.innowise.newsfeed.domain.repository

import com.innowise.newsfeed.data.network.NetworkResult
import com.innowise.newsfeed.domain.model.Article
import com.innowise.newsfeed.domain.model.Tag

interface RemoteNewsRepository {
    suspend fun getLatestArticles(
        page: Int = DEFAULT_PAGE,
        perPage: Int = DEFAULT_PAGE_SIZE,
    ): NetworkResult<List<Article>>

    suspend fun getArticles(
        page: Int = DEFAULT_PAGE,
        perPage: Int = DEFAULT_PAGE_SIZE,
        tag: String? = null,
    ): NetworkResult<List<Article>>

    suspend fun getArticle(articleId: Long): NetworkResult<Article>

    suspend fun getTags(): NetworkResult<List<Tag>>

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 30
    }
}