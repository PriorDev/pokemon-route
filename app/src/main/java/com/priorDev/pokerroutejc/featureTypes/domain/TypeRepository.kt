package com.priorDev.pokerroutejc.featureTypes.domain

import com.priorDev.pokerroutejc.core.Resource
import kotlinx.coroutines.flow.Flow

interface TypeRepository {

    suspend fun getAllTypesFlow(): Flow<Resource<List<TypeData>>>

    suspend fun getTypeFlow(typeId: Int): Flow<Resource<TypeDetailsData>>

    suspend fun getAllTypes(): Resource<List<TypeData>>

    suspend fun getType(typeId: Int): Resource<TypeDetailsData>
}
