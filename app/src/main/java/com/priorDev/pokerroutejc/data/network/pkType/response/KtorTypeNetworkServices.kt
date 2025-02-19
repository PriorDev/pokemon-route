package com.priorDev.pokerroutejc.data.network.pkType.response

import com.priorDev.pokerroutejc.data.network.EndPoints
import com.priorDev.pokerroutejc.data.network.INetWorkService
import com.priorDev.pokerroutejc.data.network.NetworkRequestData
import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.ITypeService
import javax.inject.Inject

class KtorTypeNetworkServices @Inject constructor(
    private val networkService: INetWorkService
) : ITypeService {
    override suspend fun getAllTypes(): NetworkResource {
        val requestData = NetworkRequestData(
            url = EndPoints.TYPES
        )

        return networkService.get(requestData)
    }

    override suspend fun getType(typeId: Int): NetworkResource {
        val requestData = NetworkRequestData(
            url = EndPoints.TYPES + "/$typeId"
        )

        return networkService.get(requestData)
    }
}
