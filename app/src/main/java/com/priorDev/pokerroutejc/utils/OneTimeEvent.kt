package com.priorDev.pokerroutejc.utils

import androidx.navigation.NavOptionsBuilder

sealed interface OneTimeEvent {
    class OnNavigate(
        val destinationRoute: String,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : OneTimeEvent

    object OnNavigateUp : OneTimeEvent
}
