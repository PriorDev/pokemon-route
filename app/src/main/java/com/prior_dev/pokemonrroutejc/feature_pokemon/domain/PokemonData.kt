package com.prior_dev.pokemonrroutejc.feature_pokemon.domain

import com.prior_dev.pokemonrroutejc.feature_pokemon.data.network.response.*
import com.prior_dev.pokemonrroutejc.feature_types.domain.TypeData
import com.prior_dev.pokemonrroutejc.feature_types.domain.toDomain

data class PokemonData(
    val name: String = "",
    val sprites: SpritesData = SpritesData(),
    val abilities: List<AbilityData> = emptyList(),
    val stats: List<StatData> = emptyList(),
    val types: List<TypeData> = emptyList(),
)

data class SpritesData(
    val frontDefault: String? = "",
    val frontShiny: String? = "",
    val backShiny: String? = "",
)

data class AbilityData(
    val name: String = "",
    val isHidden: Boolean = false,
)

data class StatData(
    val baseStat: Int,
    val effort: Int,
    val name: String,
)

fun PokemonResponse.toDomain(): PokemonData{
    return PokemonData(
        name = name,
        sprites = spritesResponse.toDomain(),
        abilities = abilities.map { it.toDomain() },
        stats = stats.map { it.toDomain() },
        types = types.map { it.type.toDomain() }
    )
}

fun SpritesResponse.toDomain() =
    SpritesData(
        frontDefault = frontDefault,
        frontShiny = frontShiny,
        backShiny = backShiny
    )

fun AbilityResponse.toDomain() = AbilityData(
    name = ability.name.uppercase(),
    isHidden = isHidden,
)

fun StatResponse.toDomain() = StatData(
    baseStat = baseStat,
    effort = effort,
    name = stat.name.uppercase(),
)