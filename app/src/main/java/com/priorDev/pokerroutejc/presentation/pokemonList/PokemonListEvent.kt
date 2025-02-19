package com.priorDev.pokerroutejc.presentation.pokemonList

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.ui.Routes

sealed class PokemonListEvent {
    data object OnDismiss : PokemonListEvent()
    data object OnRefresh : PokemonListEvent()
    data object OnSearch : PokemonListEvent()
    data class Navigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : PokemonListEvent()
}
