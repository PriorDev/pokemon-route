package com.priorDev.pokerroutejc.featureTypes.domain

import com.priorDev.pokerroutejc.core.ResourceFlow
import kotlinx.coroutines.flow.Flow

interface TypeRepository {

    suspend fun getAllTypesFlow(isRefresh: Boolean = false): Flow<ResourceFlow<List<TypeData>>>

    suspend fun getTypeFlow(typeId: Int): Flow<ResourceFlow<TypeDetailsData>>

    suspend fun getAllTypes(): ResourceFlow<List<TypeData>>

    suspend fun getType(typeId: Int): ResourceFlow<TypeDetailsData>
}
