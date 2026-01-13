package com.priorDev.pokerroutejc.core.data.network.pokemon.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoveResponse(
    @SerialName("move") val move: MoveNameResponse,
    @SerialName("version_group_details") val versionGroupDetails: List<VersionGroupDetailResponse>
)

@Serializable
data class MoveNameResponse(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)

@Serializable
data class MoveLearnMethodResponse(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)
