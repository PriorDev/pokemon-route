package com.priorDev.pokerroutejc.featurePokemon.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class GenerationResponse(
    val name: String,
    val url: String
)
