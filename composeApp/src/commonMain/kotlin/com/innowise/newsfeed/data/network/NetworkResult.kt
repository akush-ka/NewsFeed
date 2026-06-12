package com.innowise.newsfeed.data.network

import io.ktor.client.plugins.ResponseException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val error: NetworkError) : NetworkResult<Nothing>
}

sealed interface NetworkError {
    data class Http(val statusCode: HttpStatusCode) : NetworkError
    data object NoConnection : NetworkError
    data object Serialization : NetworkError
    data class Unknown(val message: String?) : NetworkError
}

suspend inline fun <T> safeApiCall(
    crossinline block: suspend () -> T,
): NetworkResult<T> =
    try {
        NetworkResult.Success(block())
    } catch (exception: CancellationException) {
        throw exception
    } catch (exception: ResponseException) {
        NetworkResult.Error(NetworkError.Http(exception.response.status))
    } catch (_: IOException) {
        NetworkResult.Error(NetworkError.NoConnection)
    } catch (_: SerializationException) {
        NetworkResult.Error(NetworkError.Serialization)
    } catch (exception: Exception) {
        NetworkResult.Error(NetworkError.Unknown(exception.message))
    }
