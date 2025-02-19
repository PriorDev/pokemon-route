package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData
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

    override suspend fun getType(typeId: Int): ResourceFlow<TypeDetailsData> {
        TODO("Not yet implemented")
    }
}
