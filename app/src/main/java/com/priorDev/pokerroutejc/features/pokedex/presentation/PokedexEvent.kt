package com.priorDev.pokerroutejc.features.pokedex.presentation

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.navigation.Routes

sealed class PokedexEvent {
    data class OnNavigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : PokedexEvent()
}