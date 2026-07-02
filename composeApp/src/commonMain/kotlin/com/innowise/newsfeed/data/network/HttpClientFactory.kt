package com.innowise.newsfeed.data.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://dev.to/api/"

private const val REQUEST_TIMEOUT_MS = 15_000L
private const val CONNECT_TIMEOUT_MS = 10_000L

internal fun createHttpClient(engine: HttpClientEngine? = null): HttpClient =
    if (engine != null) {
        HttpClient(engine) { configure() }
    } else {
        HttpClient { configure() }
    }

private fun HttpClientConfig<*>.configure() {
    expectSuccess = true

    defaultRequest {
        url(BASE_URL)
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
            },
        )
    }

    install(HttpTimeout) {
        requestTimeoutMillis = REQUEST_TIMEOUT_MS
        connectTimeoutMillis = CONNECT_TIMEOUT_MS
    }

    install(Logging) {
        level = LogLevel.INFO
    }
}
