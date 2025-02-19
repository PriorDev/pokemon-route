package com.priorDev.pokerroutejc.data.network.pokemon.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EffectEntryResponse(
    val effect: String,
    val language: LanguageResponse,
    @SerialName("short_effect")
    val shortEffect: String
)
