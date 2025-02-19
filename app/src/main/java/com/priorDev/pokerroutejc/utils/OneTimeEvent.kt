package com.priorDev.pokerroutejc.utils

import androidx.navigation.NavOptionsBuilder

sealed interface OneTimeEvent {
    data class OnNavigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : OneTimeEvent

    data object OnNavigateUp : OneTimeEvent
}
