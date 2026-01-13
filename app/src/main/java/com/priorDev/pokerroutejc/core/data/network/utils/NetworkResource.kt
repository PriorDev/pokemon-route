package com.priorDev.pokerroutejc.core.data.network.utils

sealed class NetworkResource<T> {
    data class Success<T>(val data: T) : NetworkResource<T>()

    data class Fail<T>(
        val error: NetworkError,
        val exception: Throwable? = null
    ) : NetworkResource<T>()
}
