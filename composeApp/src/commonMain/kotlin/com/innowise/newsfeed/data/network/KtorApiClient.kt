package com.innowise.newsfeed.data.network

import com.innowise.newsfeed.data.network.dto.ArticleDto
import com.innowise.newsfeed.data.network.dto.TagDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorApiClient(
    private val httpClient: HttpClient = createDefaultHttpClient(),
) {
    suspend fun getLatestArticles(
        page: Int,
        perPage: Int,
    ): List<ArticleDto> =
        httpClient.get("$BASE_URL/articles/latest") {
            parameter("page", page)
            parameter("per_page", perPage)
        }.body()

    suspend fun getArticles(
        page: Int,
        perPage: Int,
        tag: String? = null,
    ): List<ArticleDto> =
        httpClient.get("$BASE_URL/articles") {
            parameter("page", page)
            parameter("per_page", perPage)
            tag?.let { parameter("tag", it) }
        }.body()

    suspend fun getArticle(articleId: Long): ArticleDto =
        httpClient.get("$BASE_URL/articles/$articleId").body()

    suspend fun getTags(): List<TagDto> =
        httpClient.get("$BASE_URL/tags").body()

    private companion object {
        const val BASE_URL = "https://dev.to/api"

        fun createDefaultHttpClient(): HttpClient =
            HttpClient {
                expectSuccess = true

                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                        },
                    )
                }

                install(Logging) {
                    level = LogLevel.INFO
                }
            }
    }
}
