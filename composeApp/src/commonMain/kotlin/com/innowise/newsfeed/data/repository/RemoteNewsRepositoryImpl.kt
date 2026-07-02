package com.innowise.newsfeed.data.repository

import com.innowise.newsfeed.data.mapper.toDomain
import com.innowise.newsfeed.data.network.KtorApiClient
import com.innowise.newsfeed.data.network.safeApiCall
import com.innowise.newsfeed.domain.model.Article
import com.innowise.newsfeed.domain.model.Tag
import com.innowise.newsfeed.domain.repository.RemoteNewsRepository

internal class RemoteNewsRepositoryImpl(
    private val apiClient: KtorApiClient,
) : RemoteNewsRepository {
    override suspend fun getLatestArticles(
        page: Int,
        perPage: Int,
    ): Result<List<Article>> =
        safeApiCall {
            apiClient.getLatestArticles(
                page = page,
                perPage = perPage,
            ).map { it.toDomain() }
        }

    override suspend fun getArticles(
        page: Int,
        perPage: Int,
        tag: String?,
    ): Result<List<Article>> =
        safeApiCall {
            apiClient.getArticles(
                page = page,
                perPage = perPage,
                tag = tag,
            ).map { it.toDomain() }
        }

    override suspend fun getArticle(articleId: Long): Result<Article> =
        safeApiCall {
            apiClient.getArticle(articleId).toDomain()
        }

    override suspend fun getTags(): Result<List<Tag>> =
        safeApiCall {
            apiClient.getTags().map { it.toDomain() }
        }
}
