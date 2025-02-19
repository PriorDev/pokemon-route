package com.priorDev.pokerroutejc.featurePokemon.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityDetailsResponse(
    val name: String,
    @SerialName("effect_entries")
    val effectEntries: List<EffectEntryResponse>,
)
