package com.priorDev.pokerroutejc.core.data.network.pokemon.responses

import kotlinx.serialization.Serializable

@Serializable
data class MoveNamesResponse(
    val language: LanguageResponse,
    val name: String
)
