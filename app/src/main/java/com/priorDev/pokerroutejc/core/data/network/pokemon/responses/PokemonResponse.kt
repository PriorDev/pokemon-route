package com.priorDev.pokerroutejc.core.data.network.pokemon.responses

import com.priorDev.pokerroutejc.core.data.network.pkType.response.TypeResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("sprites") val spritesResponse: SpritesResponse,
    @SerialName("abilities") val abilities: List<AbilityResponse>,
    @SerialName("moves") val moves: List<MoveResponse>,
    @SerialName("stats") val stats: List<StatResponse>,
    @SerialName("types") val types: List<TypeContainerResponse>,
)

@Serializable
data class SpritesResponse(
    @SerialName("front_default") val frontDefault: String?,
    @SerialName("front_shiny") val frontShiny: String?,
    @SerialName("back_shiny") val backShiny: String?,
    @SerialName("other") val others: OthersSpritesResponse,
)

@Serializable
data class OthersSpritesResponse(
    @SerialName("official-artwork") val officialArtwork: OfficialArtWorkResponse
)

@Serializable
data class OfficialArtWorkResponse(
    @SerialName("front_default") val frontDefault: String?,
    @SerialName("front_shiny") val frontShiny: String?,
)

@Serializable
data class AbilityResponse(
    @SerialName("ability") val ability: AbilityNameResponse,
    @SerialName("is_hidden") val isHidden: Boolean,
)

@Serializable
data class AbilityNameResponse(
    @SerialName("name") val name: String,
)

@Serializable
data class StatResponse(
    @SerialName("base_stat") val baseStat: Int,
    @SerialName("effort") val effort: Int,
    @SerialName("stat") val stat: StatsNameResponse,
)

@Serializable
data class StatsNameResponse(
    @SerialName("name") val name: String,
)

@Serializable
data class TypeContainerResponse(
    @SerialName("slot") val slot: Int,
    @SerialName("type") val type: TypeResponse,
)
