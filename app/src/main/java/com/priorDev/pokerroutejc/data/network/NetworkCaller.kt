package com.priorDev.pokerroutejc.data.network

import com.priorDev.pokerroutejc.data.network.utils.NetworkResource
import io.ktor.client.statement.HttpResponse
import io.ktor.util.reflect.TypeInfo

interface NetworkCaller {
    suspend operator fun <T> invoke(
        typeInfo: TypeInfo,
        call: suspend () -> HttpResponse
    ): NetworkResource<T>
}
