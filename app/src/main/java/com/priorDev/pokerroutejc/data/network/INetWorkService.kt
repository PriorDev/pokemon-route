package com.priorDev.pokerroutejc.data.network

interface INetWorkService {
    suspend fun get(
        requestData: NetworkRequestData,
    ): NetworkResource
}
