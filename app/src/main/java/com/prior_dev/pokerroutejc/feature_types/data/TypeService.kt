package com.prior_dev.pokerroutejc.feature_types.data

import com.prior_dev.pokerroutejc.feature_types.data.network.response.ContainerTypeResponse
import com.prior_dev.pokerroutejc.feature_types.data.network.response.TypeDetailsResponse

interface TypeService {
    suspend fun getAllTypes(): ContainerTypeResponse?
    suspend fun getType(typeId: Int): TypeDetailsResponse?
}