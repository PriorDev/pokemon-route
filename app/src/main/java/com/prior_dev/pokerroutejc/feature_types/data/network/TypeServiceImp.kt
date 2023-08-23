package com.prior_dev.pokerroutejc.feature_types.data.network

import com.prior_dev.pokerroutejc.feature_types.data.TypeService
import com.prior_dev.pokerroutejc.feature_types.data.network.response.ContainerTypeResponse
import com.prior_dev.pokerroutejc.feature_types.data.network.response.TypeDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TypeServiceImp @Inject constructor (
    private val api: TypeApi,
) : TypeService {
    override suspend fun getAllTypes(): ContainerTypeResponse?{
        return withContext(Dispatchers.IO){
            api.getAllTypes().body()
        }
    }

    override suspend fun getType(typeId: Int): TypeDetailsResponse?{
        return withContext(Dispatchers.IO){
            api.getType("type/$typeId").body()
        }
    }
}