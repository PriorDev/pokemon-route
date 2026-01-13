package com.priorDev.pokerroutejc.features.pokemon_list.presentation

import com.priorDev.pokerroutejc.core.presentation.UiMessages

data class PokemonListState(
    val isLoading: Boolean = true,
    val uiMessages: UiMessages? = null,
    val isRefreshing: Boolean = false
)
