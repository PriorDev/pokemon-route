package com.priorDev.pokerroutejc.presentation.pokemonSearch

import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonNameData

data class PkSearchState(
    val searchText: String = "",
    val pokemonNames: List<PokemonNameData> = emptyList()
)
