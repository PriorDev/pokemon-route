package com.priorDev.pokerroutejc.presentation.pokemonList

import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonNameData
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.UiMessages

data class PokemonListState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorState: ErrorState<PokemonListEvent>? = null,
    val searchText: String = "",
    val searchResults: List<PokemonNameData> = emptyList()
)
