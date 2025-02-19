package com.priorDev.pokerroutejc.data.network.pkType

import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeDetailsResponse

interface ITypeService {
    suspend fun getAllTypes(): NetworkResource<ContainerTypeResponse>
    suspend fun getType(typeId: Int): NetworkResource<TypeDetailsResponse>
}
