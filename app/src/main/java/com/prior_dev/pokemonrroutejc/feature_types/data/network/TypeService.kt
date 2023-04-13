package com.prior_dev.pokemonrroutejc.feature_types.data.network

import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.ContainerTypeResponse
import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.TypeDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TypeService @Inject constructor (
    private val api: TypeApi,
) {
    suspend fun getAllTypes(): ContainerTypeResponse?{
        return withContext(Dispatchers.IO){
            api.getAllTypes().body()
        }
    }

    suspend fun getType(typeId: Int): TypeDetailsResponse?{
        return withContext(Dispatchers.IO){
            api.getType("type/$typeId").body()
        }
    }
}