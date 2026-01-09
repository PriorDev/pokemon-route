package com.priorDev.pokerroutejc.presentation.pokemonList

import com.priorDev.pokerroutejc.presentation.core.UiMessages

data class PokemonListState(
    val isLoading: Boolean = true,
    val uiMessages: UiMessages? = null,
    val isRefreshing: Boolean = false
)
