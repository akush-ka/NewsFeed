package com.innowise.newsfeed.domain.repository

import com.innowise.newsfeed.data.network.NetworkResult
import com.innowise.newsfeed.data.network.dto.ArticleDto
import com.innowise.newsfeed.data.network.dto.TagDto

interface RemoteNewsRepository {
    suspend fun getLatestArticles(
        page: Int = DEFAULT_PAGE,
        perPage: Int = DEFAULT_PAGE_SIZE,
    ): NetworkResult<List<ArticleDto>>

    suspend fun getArticles(
        page: Int = DEFAULT_PAGE,
        perPage: Int = DEFAULT_PAGE_SIZE,
        tag: String? = null,
    ): NetworkResult<List<ArticleDto>>

    suspend fun getArticle(articleId: Long): NetworkResult<ArticleDto>

    suspend fun getTags(): NetworkResult<List<TagDto>>

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 30
    }
}