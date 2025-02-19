package com.priorDev.pokerroutejc.data.network.pkType

import com.priorDev.pokerroutejc.data.network.EndPoints
import com.priorDev.pokerroutejc.data.network.INetWorkService
import com.priorDev.pokerroutejc.data.network.NetworkRequestData
import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeDetailsResponse
import io.ktor.util.reflect.typeInfo
import javax.inject.Inject

class TypeNetworkServices @Inject constructor(
    private val networkService: INetWorkService
) : ITypeService {
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
