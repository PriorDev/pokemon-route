package com.priorDev.pokerroutejc.data.network.pkType

import com.priorDev.pokerroutejc.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeDetailsResponse

interface TypeNetService {
    suspend fun getAllTypes(): NetworkResource<ContainerTypeResponse>
    suspend fun getType(typeId: Int): NetworkResource<TypeDetailsResponse>
}
