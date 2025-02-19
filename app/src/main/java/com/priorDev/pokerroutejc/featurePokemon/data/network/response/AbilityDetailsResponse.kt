package com.priorDev.pokerroutejc.featurePokemon.data.network.response

import com.google.gson.annotations.SerializedName

data class AbilityDetailsResponse(
    val name: String,
    @SerializedName("effect_entries")
    val effectEntries: List<EffectEntryResponse>,
)
