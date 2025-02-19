package com.priorDev.pokerroutejc.utils

import androidx.navigation.NavOptionsBuilder

interface IGlobalEventChannel {
    fun sendEvent(event: OneTimeEvent)

    fun navigate(
        route: Routes,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    fun navigateUp()
}
