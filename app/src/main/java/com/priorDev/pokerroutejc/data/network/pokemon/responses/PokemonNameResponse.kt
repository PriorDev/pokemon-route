package com.priorDev.pokerroutejc.data.network.pokemon.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContainerPokemonNameResponse(
    @SerialName("next") val next: String?,
    @SerialName("results") val pokemons: List<PokemonNameResponse>
)

@Serializable
data class PokemonNameResponse(
    @SerialName("url") val url: String,
    @SerialName("name") val name: String,
)
