package com.priorDev.pokerroutejc.presentation.pokedex

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.ui.Routes

sealed class PokedexEvent {
    data class OnNavigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : PokedexEvent()

    data object OnRetryGetPokedexEntries : PokedexEvent()
}
