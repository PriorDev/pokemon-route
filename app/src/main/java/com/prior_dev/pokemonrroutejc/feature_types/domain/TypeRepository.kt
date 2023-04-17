package com.prior_dev.pokemonrroutejc.feature_types.domain

import com.prior_dev.pokemonrroutejc.core.Resource
import kotlinx.coroutines.flow.Flow

interface TypeRepository {

    suspend fun getAllTypes(): Flow<Resource<List<TypeData>>>

    suspend fun getType(typeId: Int): Flow<Resource<TypeDetailsData>>
}