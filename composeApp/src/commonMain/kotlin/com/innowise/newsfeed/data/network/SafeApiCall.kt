package com.innowise.newsfeed.data.network

import kotlinx.coroutines.CancellationException

internal suspend inline fun <T> safeApiCall(
    crossinline block: suspend () -> T,
): Result<T> =
    try {
        Result.success(block())
    } catch (exception: CancellationException) {
        throw exception
    } catch (exception: Exception) {
        Result.failure(exception)
    }
