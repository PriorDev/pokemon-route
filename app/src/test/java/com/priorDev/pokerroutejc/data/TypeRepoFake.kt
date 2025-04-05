package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.domain.type.dragonTypeDetails
import com.priorDev.pokerroutejc.domain.type.electricTypeDetails
import com.priorDev.pokerroutejc.domain.type.fairyTypeDetails
import com.priorDev.pokerroutejc.domain.type.ghostTypeDetails
import com.priorDev.pokerroutejc.domain.type.groundTypeDetails
import com.priorDev.pokerroutejc.domain.type.waterTypeDetails
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData
import com.priorDev.pokerroutejc.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TypeRepoFake : TypeRepo {
    var getAllTypeFlow = flow<ResourceFlow<List<TypeData>>> { }
    override suspend fun getAllTypesFlow(isRefresh: Boolean): Flow<ResourceFlow<List<TypeData>>> {
        return getAllTypeFlow
    }

    override suspend fun getTypeFlow(typeId: Int): Flow<ResourceFlow<TypeDetailsData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTypes(isRefresh: Boolean): ResourceFlow<List<TypeData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getType(typeId: Int): Resource<TypeDetailsData> {
        return when (typeId) {
            11 -> Resource.Success(waterTypeDetails)
            16 -> Resource.Success(dragonTypeDetails)
            18 -> Resource.Success(fairyTypeDetails)
            8 -> Resource.Success(ghostTypeDetails)
            5 -> Resource.Success(groundTypeDetails)
            13 -> Resource.Success(electricTypeDetails)
            else -> Resource.Error(networkErrorType = NetworkError.EmptyContent)
        }
    }
}
