package com.priorDev.pokerroutejc.featurePokemon.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class MoveNamesResponse(
    val language: LanguageResponse,
    val name: String
)
