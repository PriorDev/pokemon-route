package com.priorDev.pokerroutejc.data.network

interface INetWorkService {
    suspend fun <T> get(
        requestData: NetworkRequestData
    ): NetworkResource<T>
}
