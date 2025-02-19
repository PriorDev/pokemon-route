package com.priorDev.pokerroutejc.data.network

import io.ktor.client.statement.HttpResponse

interface INetworkCaller {
    suspend operator fun invoke(
        call: suspend () -> HttpResponse
    ): NetworkResource
}
