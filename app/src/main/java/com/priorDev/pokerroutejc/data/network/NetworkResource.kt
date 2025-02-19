package com.priorDev.pokerroutejc.data.network

sealed class NetworkResource<T> {
    data class Success<T>(val data: T) : NetworkResource<T>()

    data class Fail<T>(
        val error: NetworkError,
        val exeption: Throwable? = null
    ) : NetworkResource<T>()
}
