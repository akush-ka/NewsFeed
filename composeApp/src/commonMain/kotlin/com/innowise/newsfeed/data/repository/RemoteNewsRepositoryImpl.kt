package com.innowise.newsfeed.data.repository

import com.innowise.newsfeed.data.network.KtorApiClient
import com.innowise.newsfeed.data.network.NetworkResult
import com.innowise.newsfeed.data.network.dto.ArticleDto
import com.innowise.newsfeed.data.network.dto.TagDto
import com.innowise.newsfeed.data.network.safeApiCall
import com.innowise.newsfeed.domain.repository.RemoteNewsRepository

class RemoteNewsRepositoryImpl(
    private val apiClient: KtorApiClient = KtorApiClient(),
) : RemoteNewsRepository {
    override suspend fun getLatestArticles(
        page: Int,
        perPage: Int,
    ): NetworkResult<List<ArticleDto>> =
        safeApiCall {
            apiClient.getLatestArticles(
                page = page,
                perPage = perPage,
            )
        }

    override suspend fun getArticles(
        page: Int,
        perPage: Int,
        tag: String?,
    ): NetworkResult<List<ArticleDto>> =
        safeApiCall {
            apiClient.getArticles(
                page = page,
                perPage = perPage,
                tag = tag,
            )
        }

    override suspend fun getArticle(articleId: Long): NetworkResult<ArticleDto> =
        safeApiCall {
            apiClient.getArticle(articleId)
        }

    override suspend fun getTags(): NetworkResult<List<TagDto>> =
        safeApiCall {
            apiClient.getTags()
        }
}
