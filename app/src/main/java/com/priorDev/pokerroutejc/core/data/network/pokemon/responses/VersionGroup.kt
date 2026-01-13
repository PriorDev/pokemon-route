package com.priorDev.pokerroutejc.core.data.network.pokemon.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionGroupDetailResponse(
    @SerialName("level_learned_at") val levelLearnedAt: Int,
    @SerialName("move_learn_method") val moveLearnMethod: MoveLearnMethodResponse,
    @SerialName("version_group") val versionGroup: VersionGroupResponse
)

@Serializable
data class VersionGroupResponse(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)
