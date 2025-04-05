package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData
import com.priorDev.pokerroutejc.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TypeRepo {

    suspend fun getAllTypesFlow(isRefresh: Boolean = false): Flow<ResourceFlow<List<TypeData>>>

    suspend fun getTypeFlow(typeId: Int): Flow<ResourceFlow<TypeDetailsData>>

    suspend fun getAllTypes(isRefresh: Boolean = false): ResourceFlow<List<TypeData>>

    suspend fun getType(typeId: Int): Resource<TypeDetailsData>
}
