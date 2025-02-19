package com.priorDev.pokerroutejc.data.network

import io.ktor.util.reflect.TypeInfo

interface INetWorkService {
    suspend fun <T> get(
        requestData: NetworkRequestData
    ): NetworkResource<T>
}
