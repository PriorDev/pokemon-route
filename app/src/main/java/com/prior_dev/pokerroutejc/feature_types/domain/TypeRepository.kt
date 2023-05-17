package com.prior_dev.pokerroutejc.feature_types.domain

import com.prior_dev.pokerroutejc.core.Resource
import kotlinx.coroutines.flow.Flow

interface TypeRepository {

    suspend fun getAllTypesFlow(): Flow<Resource<List<TypeData>>>

    suspend fun getTypeFlow(typeId: Int): Flow<Resource<TypeDetailsData>>

    suspend fun getAllTypes(): Resource<List<TypeData>>

    suspend fun getType(typeId: Int): Resource<TypeDetailsData>
}