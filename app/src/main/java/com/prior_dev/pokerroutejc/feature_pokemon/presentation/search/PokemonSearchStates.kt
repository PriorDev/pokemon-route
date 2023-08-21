package com.prior_dev.pokerroutejc.feature_pokemon.presentation.search

import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonNameData

data class PokemonSearchStates (
    val pokemons: List<PokemonNameData> = emptyList()
)