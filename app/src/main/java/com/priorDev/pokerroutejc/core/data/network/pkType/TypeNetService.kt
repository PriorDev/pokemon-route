package com.priorDev.pokerroutejc.core.data.network.pkType

import com.priorDev.pokerroutejc.core.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.core.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.core.data.network.pkType.response.TypeDetailsResponse

interface TypeNetService {
    suspend fun getAllTypes(): NetworkResource<ContainerTypeResponse>
    suspend fun getType(typeId: Int): NetworkResource<TypeDetailsResponse>
}
