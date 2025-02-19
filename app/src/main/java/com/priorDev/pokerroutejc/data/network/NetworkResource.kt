package com.priorDev.pokerroutejc.data.network

import io.ktor.client.statement.HttpResponse

sealed class NetworkResource {
    data class Success(val response: HttpResponse) : NetworkResource()
    data class Fail(val error: NetworkError) : NetworkResource()
}
