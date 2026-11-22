package com.example.camellando.data.repository

import kotlinx.coroutines.CancellationException

internal suspend fun <T> repositorySafeCall(
    action: suspend () -> T
): Result<T> =
    try {
        Result.success(action())
    } catch (cancellation: CancellationException) {
        throw cancellation
    } catch (exception: Exception) {
        Result.failure(exception)
    }