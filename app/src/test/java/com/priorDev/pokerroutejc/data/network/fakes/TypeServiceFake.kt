package com.priorDev.pokerroutejc.data.network.fakes

import com.priorDev.pokerroutejc.data.network.NetworkError
import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.ITypeService
import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeDetailsResponse

class TypeServiceFake : ITypeService {
    var getAllTypeResponse: NetworkResource<ContainerTypeResponse> = NetworkResource.Fail(NetworkError.None)
    override suspend fun getAllTypes(): NetworkResource<ContainerTypeResponse> {
        return getAllTypeResponse
    }

    var getTypeResponse: NetworkResource<TypeDetailsResponse> = NetworkResource.Fail(NetworkError.None)
    override suspend fun getType(typeId: Int): NetworkResource<TypeDetailsResponse> {
        return getTypeResponse
    }
}
