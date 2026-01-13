package com.priorDev.pokerroutejc.core.data.network

import com.apollographql.apollo3.api.Query
import com.priorDev.pokerroutejc.core.data.network.utils.NetworkResource

interface ApolloCaller {
    suspend operator fun <T : Query.Data> invoke(
        query: Query<T>
    ): NetworkResource<T>
}
