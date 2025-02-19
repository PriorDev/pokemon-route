package com.priorDev.pokerroutejc.utils

import androidx.navigation.NavOptionsBuilder

interface IGlobalEventChannel {
    fun sendEvent(event: OneTimeEvent)

    fun onNavigate(
        destinationRoute: String,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    fun <T: Any> onNavigate(
        route: T,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    fun sendNavigateUpEvent()
}
