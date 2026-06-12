package com.innowise.newsfeed.data.repository

import com.innowise.newsfeed.data.network.NetworkResult
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.fail

class RemoteNewsRepositorySmokeTest {
    @Test
    fun latestArticlesRequestReturnsData() = runBlocking {
        val repository = RemoteNewsRepositoryImpl()

        when (val result = repository.getLatestArticles(perPage = 1)) {
            is NetworkResult.Success -> assertTrue(result.data.isNotEmpty())
            is NetworkResult.Error -> fail("Request failed: ${result.error}")
        }
    }
}
