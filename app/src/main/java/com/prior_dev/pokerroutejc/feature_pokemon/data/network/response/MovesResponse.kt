package com.prior_dev.pokerroutejc.feature_pokemon.data.network.response

import com.google.gson.annotations.SerializedName

data class MoveResponse(
    @SerializedName("move") val move: MoveNameResponse,
    @SerializedName("version_group_details") val versionGroupDetails: List<VersionGroupDetailResponse>
)

data class MoveNameResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

data class MoveLearnMethodResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

