package com.innowise.newsfeed.data.network

import com.innowise.newsfeed.data.network.dto.ArticleDto
import com.innowise.newsfeed.data.network.dto.TagDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class KtorApiClient(
    private val httpClient: HttpClient,
) {
    suspend fun getLatestArticles(
        page: Int,
        perPage: Int,
    ): List<ArticleDto> =
        httpClient.get("$ARTICLES_PATH/latest") {
            parameter(PARAM_PAGE, page)
            parameter(PARAM_PER_PAGE, perPage)
        }.body()

    suspend fun getArticles(
        page: Int,
        perPage: Int,
        tag: String? = null,
    ): List<ArticleDto> =
        httpClient.get(ARTICLES_PATH) {
            parameter(PARAM_PAGE, page)
            parameter(PARAM_PER_PAGE, perPage)
            tag?.let { parameter(PARAM_TAG, it) }
        }.body()

    suspend fun getArticle(articleId: Long): ArticleDto =
        httpClient.get("$ARTICLES_PATH/$articleId").body()

    suspend fun getTags(): List<TagDto> =
        httpClient.get(TAGS_PATH).body()

    private companion object {
        const val ARTICLES_PATH = "articles"
        const val TAGS_PATH = "tags"
        const val PARAM_PAGE = "page"
        const val PARAM_PER_PAGE = "per_page"
        const val PARAM_TAG = "tag"
    }
}
