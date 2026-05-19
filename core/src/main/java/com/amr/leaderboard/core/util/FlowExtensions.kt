package com.amr.leaderboard.core.util

import com.amr.leaderboard.core.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.map

fun <T> Flow<T>.asResource(): Flow<Resource<T>> {
    return this
        .map { Resource.Success(it) as Resource<T> }
        .onStart { emit(Resource.Loading()) }
        .catch { e -> emit(Resource.Error(e.message ?: "Unknown Error")) }
}

//private fun <T, R> Flow<T>.map(transform: suspend (value: T) -> R): Flow<R> = kotlinx.coroutines.flow.map(transform)

