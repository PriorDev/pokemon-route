package com.priorDev.pokerroutejc.data.network.fakes

import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.TypeNetService
import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeDetailsResponse

class TypeNetServiceFake : TypeNetService {
    var getAllTypeResponse: NetworkResource<ContainerTypeResponse> = NetworkResource.Fail(
        NetworkError.None)
    override suspend fun getAllTypes(): NetworkResource<ContainerTypeResponse> {
        return getAllTypeResponse
    }

    var getTypeResponse: NetworkResource<TypeDetailsResponse> = NetworkResource.Fail(NetworkError.None)
    override suspend fun getType(typeId: Int): NetworkResource<TypeDetailsResponse> {
        return getTypeResponse
    }
}
