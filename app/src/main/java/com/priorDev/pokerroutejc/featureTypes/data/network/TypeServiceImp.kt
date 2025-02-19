package com.priorDev.pokerroutejc.featureTypes.data.network

import com.priorDev.pokerroutejc.featureTypes.data.TypeService
import com.priorDev.pokerroutejc.featureTypes.data.network.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.featureTypes.data.network.response.TypeDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TypeServiceImp @Inject constructor(
    private val api: TypeApi,
) : TypeService {
    override suspend fun getAllTypes(): ContainerTypeResponse? {
        return withContext(Dispatchers.IO) {
            api.getAllTypes().body()
        }
    }

    override suspend fun getType(typeId: Int): TypeDetailsResponse? {
        return withContext(Dispatchers.IO) {
            api.getType("type/$typeId").body()
        }
    }
}
