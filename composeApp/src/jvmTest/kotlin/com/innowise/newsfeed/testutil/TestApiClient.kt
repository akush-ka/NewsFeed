package com.innowise.newsfeed.testutil

import com.innowise.newsfeed.data.network.KtorApiClient
import com.innowise.newsfeed.data.network.createHttpClient
import io.ktor.client.engine.mock.MockEngine

internal fun createTestApiClient(engine: MockEngine): KtorApiClient =
    KtorApiClient(createHttpClient(engine))
