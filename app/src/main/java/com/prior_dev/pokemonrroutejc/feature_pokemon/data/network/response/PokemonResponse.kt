package com.prior_dev.pokemonrroutejc.feature_pokemon.data.network.response

import com.google.gson.annotations.SerializedName
import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.TypeResponse

data class PokemonResponse(
    @SerializedName("name") val name: String,
    @SerializedName("sprites") val spritesResponse: SpritesResponse,
    @SerializedName("abilities") val abilities: List<AbilityResponse>,
    @SerializedName("stats") val stats: List<StatResponse>,
    @SerializedName("types") val types: List<TypeContainerResponse>,
)

data class SpritesResponse(
    @SerializedName("front_default") val frontDefault: String?,
    @SerializedName("front_shiny") val frontShiny: String?,
    @SerializedName("back_shiny") val backShiny: String?,
)

data class AbilityResponse(
    @SerializedName("ability") val ability: AbilityNameResponse,
    @SerializedName("is_hidden") val isHidden: Boolean,
)

data class AbilityNameResponse(
    @SerializedName("name") val name: String,
)

data class StatResponse(
    @SerializedName("base_stat") val baseStat: Int,
    @SerializedName("effort") val effort: Int,
    @SerializedName("stat") val stat: StatsNameResponse,
)

data class StatsNameResponse(
    @SerializedName("name") val name: String,
)

data class TypeContainerResponse(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: TypeResponse,
)