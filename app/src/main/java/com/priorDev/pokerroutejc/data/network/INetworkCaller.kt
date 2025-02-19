package com.priorDev.pokerroutejc.data.network

import io.ktor.client.statement.HttpResponse
import io.ktor.util.reflect.TypeInfo

interface INetworkCaller {
    suspend operator fun <T> invoke(
        typeInfo: TypeInfo,
        call: suspend () -> HttpResponse
    ): NetworkResource<T>
}
