package com.priorDev.pokerroutejc.features.pokemon_search.presentation

import com.priorDev.pokerroutejc.core.domain.pokemon.models.PokemonNameData

data class PkSearchState(
    val searchText: String = "",
    val pokemonNames: List<PokemonNameData> = emptyList()
)
