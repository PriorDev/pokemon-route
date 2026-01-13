package com.priorDev.pokerroutejc.features.pokedex_selection.presentation

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.navigation.Routes

sealed class VersionGroupEvent {
    data class OnNavigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : VersionGroupEvent()

    data object OnToggleOrder : VersionGroupEvent()
}
