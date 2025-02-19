package com.priorDev.pokerroutejc.data.network

import com.apollographql.apollo3.api.Query
import com.priorDev.pokerroutejc.data.network.utils.NetworkResource

interface ApolloCaller {
    suspend operator fun <T : Query.Data> invoke(
        query: Query<T>
    ): NetworkResource<T>
}
