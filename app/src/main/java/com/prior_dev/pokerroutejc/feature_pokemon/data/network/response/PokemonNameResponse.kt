package com.prior_dev.pokerroutejc.feature_pokemon.data.network.response

import com.google.gson.annotations.SerializedName

data class ContainerPokemonNameResponse(
    @SerializedName("next") val next: String?,
    @SerializedName("results") val pokemons: List<PokemonNameResponse>
)

data class PokemonNameResponse(
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String,
)
