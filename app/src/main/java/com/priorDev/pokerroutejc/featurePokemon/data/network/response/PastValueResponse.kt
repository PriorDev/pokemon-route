package com.priorDev.pokerroutejc.featurePokemon.data.network.response

import com.google.gson.annotations.SerializedName

data class PastValueResponse(
    val accuracy: Int,
    @SerializedName("effect_chance")
    val effectChance: Any,
    @SerializedName("effect_entries")
    val effectEntries: List<Any>,
    val power: Any,
    val pp: Any,
    val type: Any,
    @SerializedName("version_group")
    val versionGroup: VersionGroupResponse
)
