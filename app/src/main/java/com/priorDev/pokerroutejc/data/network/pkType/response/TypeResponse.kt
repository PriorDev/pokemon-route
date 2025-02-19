package com.priorDev.pokerroutejc.data.network.pkType.response

import com.google.gson.annotations.SerializedName

data class ContainerTypeResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("results") val types: List<TypeResponse>
)

data class TypeResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
