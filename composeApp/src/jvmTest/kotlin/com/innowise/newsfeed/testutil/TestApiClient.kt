package com.innowise.newsfeed.testutil

import com.innowise.newsfeed.data.network.KtorApiClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun createTestApiClient(engine: MockEngine): KtorApiClient =
    KtorApiClient(createTestHttpClient(engine))

internal fun createTestHttpClient(engine: MockEngine): HttpClient =
    HttpClient(engine) {
        expectSuccess = true

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                },
            )
        }
    }
