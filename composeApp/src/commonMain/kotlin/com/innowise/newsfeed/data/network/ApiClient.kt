package com.innowise.newsfeed.data.network

import com.innowise.newsfeed.data.network.dto.ArticleDto
import com.innowise.newsfeed.data.network.dto.TagDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiClient(
    private val httpClient: HttpClient,
) {
    suspend fun getLatestArticles(
        page: Int = DEFAULT_PAGE,
        perPage: Int = DEFAULT_PAGE_SIZE,
        tag: String? = null,
    ): List<ArticleDto> =
        httpClient.get(if (tag == null) LATEST_ARTICLES_URL else ARTICLE_URL) {
            parameter("page", page)
            parameter("per_page", perPage)
            tag?.let { parameter("tag", it) }
        }.body()

    suspend fun getArticle(articleId: Long): ArticleDto =
        httpClient.get("$ARTICLE_URL/$articleId").body()

    suspend fun getTags(): List<TagDto> =
        httpClient.get(TAGS_URL).body()

    private companion object {
        const val BASE_URL = "https://dev.to/api"
        const val LATEST_ARTICLES_URL = "$BASE_URL/articles/latest"
        const val ARTICLE_URL = "$BASE_URL/articles"
        const val TAGS_URL = "$BASE_URL/tags"
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 30
    }
}
