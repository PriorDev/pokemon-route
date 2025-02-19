package com.priorDev.pokerroutejc.featureTypes.data

import com.priorDev.pokerroutejc.featureTypes.data.network.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.featureTypes.data.network.response.TypeDetailsResponse

interface TypeService {
    suspend fun getAllTypes(): ContainerTypeResponse?
    suspend fun getType(typeId: Int): TypeDetailsResponse?
}
