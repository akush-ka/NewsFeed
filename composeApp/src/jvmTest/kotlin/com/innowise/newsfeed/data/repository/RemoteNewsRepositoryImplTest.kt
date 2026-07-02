package com.innowise.newsfeed.data.repository

import com.innowise.newsfeed.testutil.createTestApiClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.ResponseException
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RemoteNewsRepositoryImplTest {
    @Test
    fun getLatestArticlesRequestsLatestArticlesEndpoint() = runBlocking {
        val repository = createRepository(MockEngine { request ->
            assertEquals(HttpMethod.Get, request.method)
            assertEquals("dev.to", request.url.host)
            assertEquals("/api/articles/latest", request.url.encodedPath)
            assertEquals("1", request.url.parameters["page"])
            assertEquals("1", request.url.parameters["per_page"])

            respondJson(ARTICLE_LIST_RESPONSE)
        })

        val result = repository.getLatestArticles(perPage = 1)

        val articles = assertSuccess(result)
        assertEquals(1, articles.single().id)
        assertEquals("Latest article", articles.single().title)
    }

    @Test
    fun getArticlesRequestsArticlesEndpointWithTag() = runBlocking {
        val repository = createRepository(MockEngine { request ->
            assertEquals(HttpMethod.Get, request.method)
            assertEquals("/api/articles", request.url.encodedPath)
            assertEquals("2", request.url.parameters["page"])
            assertEquals("10", request.url.parameters["per_page"])
            assertEquals("kotlin", request.url.parameters["tag"])

            respondJson(ARTICLE_LIST_RESPONSE)
        })

        val result = repository.getArticles(
            page = 2,
            perPage = 10,
            tag = "kotlin",
        )

        val articles = assertSuccess(result)
        assertEquals(listOf("kotlin"), articles.single().tags)
    }

    @Test
    fun getArticleRequestsArticleDetailsEndpoint() = runBlocking {
        val repository = createRepository(MockEngine { request ->
            assertEquals(HttpMethod.Get, request.method)
            assertEquals("/api/articles/42", request.url.encodedPath)

            respondJson(ARTICLE_RESPONSE)
        })

        val result = repository.getArticle(42)

        val article = assertSuccess(result)
        assertEquals(42, article.id)
        assertEquals("Article details", article.title)
    }

    @Test
    fun getTagsRequestsTagsEndpoint() = runBlocking {
        val repository = createRepository(MockEngine { request ->
            assertEquals(HttpMethod.Get, request.method)
            assertEquals("/api/tags", request.url.encodedPath)

            respondJson(TAG_LIST_RESPONSE)
        })

        val result = repository.getTags()

        val tags = assertSuccess(result)
        assertEquals(7, tags.single().id)
        assertEquals("kotlin", tags.single().name)
    }

    @Test
    fun httpErrorReturnsFailure() = runBlocking {
        val repository = createRepository(MockEngine {
            respond(
                content = "",
                status = HttpStatusCode.InternalServerError,
            )
        })

        val result = repository.getLatestArticles(perPage = 1)

        assertTrue(result.isFailure)
        val exception = assertIs<ResponseException>(result.exceptionOrNull())
        assertEquals(HttpStatusCode.InternalServerError, exception.response.status)
    }

    private fun createRepository(mockEngine: MockEngine): RemoteNewsRepositoryImpl =
        RemoteNewsRepositoryImpl(
            apiClient = createTestApiClient(mockEngine),
        )

    private fun MockRequestHandleScope.respondJson(content: String) =
        respond(
            content = content,
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
        )

    private fun <T> assertSuccess(result: Result<T>): T =
        result.getOrElse { error("Expected success, got $it") }

    private companion object {
        const val ARTICLE_LIST_RESPONSE = """
            [
                {
                    "id": 1,
                    "title": "Latest article",
                    "description": "Short description",
                    "url": "https://dev.to/news/latest-article",
                    "tag_list": ["kotlin"],
                    "unknown_field": "ignored"
                }
            ]
        """

        const val ARTICLE_RESPONSE = """
            {
                "id": 42,
                "title": "Article details",
                "description": "Details description",
                "url": "https://dev.to/news/article-details",
                "tag_list": ["android"]
            }
        """

        const val TAG_LIST_RESPONSE = """
            [
                {
                    "id": 7,
                    "name": "kotlin"
                }
            ]
        """
    }
}
