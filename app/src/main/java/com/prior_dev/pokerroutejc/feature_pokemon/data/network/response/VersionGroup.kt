package com.prior_dev.pokerroutejc.feature_pokemon.data.network.response

import com.google.gson.annotations.SerializedName

data class VersionGroupDetailResponse(
    @SerializedName("level_learned_at") val levelLearnedAt: Int,
    @SerializedName("move_learn_method") val moveLearnMethod: MoveLearnMethodResponse,
    @SerializedName("version_group") val versionGroup: VersionGroupResponse
)

data class VersionGroupResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)