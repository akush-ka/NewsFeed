package com.innowise.newsfeed.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://dev.to/api"

internal fun createHttpClient(): HttpClient =
    HttpClient {
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

        install(Logging) {
            level = LogLevel.INFO
        }
    }