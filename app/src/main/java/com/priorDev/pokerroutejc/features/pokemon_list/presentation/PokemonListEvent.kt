package com.priorDev.pokerroutejc.features.pokemon_list.presentation

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.navigation.Routes

sealed class PokemonListEvent {
    data object OnDismiss : PokemonListEvent()
    data object OnRefresh : PokemonListEvent()
    data object OnSearch : PokemonListEvent()
    data class Navigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : PokemonListEvent()
}
