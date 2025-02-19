package com.priorDev.pokerroutejc.data.network.pkType.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContainerTypeResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("results")
    val types: List<TypeResponse>
)

@Serializable
data class TypeResponse(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)
