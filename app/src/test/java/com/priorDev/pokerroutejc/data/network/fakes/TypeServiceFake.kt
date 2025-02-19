package com.priorDev.pokerroutejc.data.network.fakes

import com.priorDev.pokerroutejc.data.network.NetworkError
import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.ITypeService
import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse

class TypeServiceFake: ITypeService {
    var returnNetworkResource: NetworkResource = NetworkResource.Fail(NetworkError.None)

    override suspend fun getAllTypes(): NetworkResource<ContainerTypeResponse> {
        return returnNetworkResource
    }

    override suspend fun getType(typeId: Int): NetworkResource {
        return returnNetworkResource
    }
}