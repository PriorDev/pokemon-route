package com.prior_dev.pokerroutejc.feature_pokemon.domain

import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.*
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData
import com.prior_dev.pokerroutejc.feature_types.domain.toDomain

data class PokemonData(
    val id: Int = 0,
    val name: String = "",
    val sprites: SpritesData = SpritesData(),
    val abilities: List<AbilityData> = emptyList(),
    val stats: List<StatData> = emptyList(),
    val types: List<TypeData> = emptyList(),
    val moves: List<MoveData> = emptyList()
)

data class SpritesData(
    val frontDefault: String? = "",
    val frontShiny: String? = "",
    val backShiny: String? = "",
    val others: OthersSpritesData? = null,
)

data class OthersSpritesData(
    val officialArtFrontDefault: String?,
    val officialArtFrontShiny: String?,
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
        id = id,
        name = name,
        sprites = spritesResponse.toDomain(),
        abilities = abilities.map { it.toDomain() },
        stats = stats.map { it.toDomain() },
        types = types.map { it.type.toDomain() },
        moves = moves.map { it.toDomain() }
    )
}

fun SpritesResponse.toDomain() =
    SpritesData(
        frontDefault = frontDefault,
        frontShiny = frontShiny,
        backShiny = backShiny,
        others = OthersSpritesData(
            officialArtFrontDefault = others.officialArtwork.frontDefault,
            officialArtFrontShiny = others.officialArtwork.frontShiny,
        )
    )

fun AbilityResponse.toDomain() = AbilityData(
    name = ability.name.uppercase(),
    isHidden = isHidden,
)

fun StatResponse.toDomain(): StatData{
    val nameAbbr = when(stat.name.lowercase()) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }

    return StatData(
        baseStat = baseStat,
        effort = effort,
        name = nameAbbr,
    )
}