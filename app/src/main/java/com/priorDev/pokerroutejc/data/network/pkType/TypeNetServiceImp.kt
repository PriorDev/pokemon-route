package com.priorDev.pokerroutejc.data.network.pkType

import com.priorDev.pokerroutejc.data.network.utils.EndPoints
import com.priorDev.pokerroutejc.data.network.NetworkService
import com.priorDev.pokerroutejc.data.network.utils.NetworkRequestData
import com.priorDev.pokerroutejc.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeDetailsResponse
import io.ktor.util.reflect.typeInfo
import javax.inject.Inject

class TypeNetServiceImp @Inject constructor(
    private val networkService: NetworkService
) : TypeNetService {
    override suspend fun getAllTypes(): NetworkResource<ContainerTypeResponse> {
        val requestData = NetworkRequestData(
            url = EndPoints.TYPES,
            typeInfo = typeInfo<ContainerTypeResponse>()
        )

        return networkService.get(requestData)
    }

    override suspend fun getType(typeId: Int): NetworkResource<TypeDetailsResponse> {
        val requestData = NetworkRequestData(
            url = EndPoints.TYPES,
            segments = listOf(typeId.toString()),
            typeInfo = typeInfo<TypeDetailsResponse>()
        )

        return networkService.get(requestData)
    }
}
