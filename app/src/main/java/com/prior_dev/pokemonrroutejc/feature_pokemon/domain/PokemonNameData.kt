package com.prior_dev.pokemonrroutejc.feature_pokemon.domain

import com.prior_dev.pokemonrroutejc.feature_pokemon.data.database.PokemonNameEntity

data class PokemonNameData(
    val name: String
)

fun PokemonNameEntity.toDomain() =
    PokemonNameData(
        name = name.uppercase()
    )
