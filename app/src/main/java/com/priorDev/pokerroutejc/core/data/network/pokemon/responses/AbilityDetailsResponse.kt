package com.priorDev.pokerroutejc.core.data.network.pokemon.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityDetailsResponse(
    val name: String,
    @SerialName("effect_entries")
    val effectEntries: List<EffectEntryResponse>,
)
