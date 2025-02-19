package com.priorDev.pokerroutejc.data.network.pkType

import com.priorDev.pokerroutejc.data.network.NetworkResource

interface ITypeService {
    suspend fun getAllTypes(): NetworkResource
    suspend fun getType(typeId: Int): NetworkResource
}
