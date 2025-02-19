package com.priorDev.pokerroutejc.data.network

import com.priorDev.pokerroutejc.data.network.utils.NetworkRequestData
import com.priorDev.pokerroutejc.data.network.utils.NetworkResource

interface NetworkService {
    suspend fun <T> get(
        requestData: NetworkRequestData
    ): NetworkResource<T>
}
