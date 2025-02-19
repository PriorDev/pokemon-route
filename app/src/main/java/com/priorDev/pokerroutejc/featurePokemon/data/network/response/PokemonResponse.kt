package com.priorDev.pokerroutejc.featurePokemon.data.network.response

import com.google.gson.annotations.SerializedName
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeResponse

data class PokemonResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sprites") val spritesResponse: SpritesResponse,
    @SerializedName("abilities") val abilities: List<AbilityResponse>,
    @SerializedName("moves") val moves: List<MoveResponse>,
    @SerializedName("stats") val stats: List<StatResponse>,
    @SerializedName("types") val types: List<TypeContainerResponse>,
)

data class SpritesResponse(
    @SerializedName("front_default") val frontDefault: String?,
    @SerializedName("front_shiny") val frontShiny: String?,
    @SerializedName("back_shiny") val backShiny: String?,
    @SerializedName("other") val others: OthersSpritesResponse,
)

data class OthersSpritesResponse(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtWorkResponse
)

data class OfficialArtWorkResponse(
    @SerializedName("front_default") val frontDefault: String?,
    @SerializedName("front_shiny") val frontShiny: String?,
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
