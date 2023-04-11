package com.prior_dev.pokemonrroutejc.feature_types.data.network

import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.ContainerTypeResponse
import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.TypeDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface TypeApi {
    @GET("type")
    suspend fun getAllTypes(): Response<ContainerTypeResponse>

    @GET
    suspend fun getType(@Url type: String): Response<TypeDetailsResponse>
}