package com.priorDev.pokerroutejc.data.network

import io.ktor.client.statement.HttpResponse

sealed class NetworkResource<T> {
    data class Success<T>(val data: T) : NetworkResource<T>()
    data class Fail<T>(val error: NetworkError) : NetworkResource<T>()
}
