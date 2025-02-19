package com.priorDev.pokerroutejc.data.network.pokemon.responses

import kotlinx.serialization.Serializable

@Serializable
data class GenerationResponse(
    val name: String,
    val url: String
)
