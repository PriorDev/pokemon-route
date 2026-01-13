package com.priorDev.pokerroutejc.core.data.network

import com.priorDev.pokerroutejc.core.data.network.utils.NetworkRequestData
import com.priorDev.pokerroutejc.core.data.network.utils.NetworkResource

interface NetworkService {
    suspend fun <T> get(
        requestData: NetworkRequestData
    ): NetworkResource<T>
}
