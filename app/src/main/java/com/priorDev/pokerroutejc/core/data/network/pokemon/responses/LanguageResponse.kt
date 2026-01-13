package com.priorDev.pokerroutejc.core.data.network.pokemon.responses

import kotlinx.serialization.Serializable

@Serializable
data class LanguageResponse(
    val name: String,
    val url: String
)
