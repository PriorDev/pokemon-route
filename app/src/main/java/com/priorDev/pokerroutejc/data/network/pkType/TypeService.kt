package com.priorDev.pokerroutejc.data.network.pkType

import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeDetailsResponse

interface TypeService {
    suspend fun getAllTypes(): ContainerTypeResponse?
    suspend fun getType(typeId: Int): TypeDetailsResponse?
}
