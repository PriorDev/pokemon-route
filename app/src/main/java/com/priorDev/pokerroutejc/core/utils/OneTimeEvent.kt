package com.priorDev.pokerroutejc.core.utils

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.navigation.Routes

sealed interface OneTimeEvent {
    data class OnNavigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : OneTimeEvent

    data object OnNavigateUp : OneTimeEvent
}
