package com.priorDev.pokerroutejc.data.network.pkType

import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface TypeApi {
    @GET("type")
    suspend fun getAllTypes(): Response<ContainerTypeResponse>

    @GET
    suspend fun getType(@Url type: String): Response<TypeDetailsResponse>
}
