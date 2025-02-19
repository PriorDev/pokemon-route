package com.priorDev.pokerroutejc.featurePokemon.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class LanguageResponse(
    val name: String,
    val url: String
)
