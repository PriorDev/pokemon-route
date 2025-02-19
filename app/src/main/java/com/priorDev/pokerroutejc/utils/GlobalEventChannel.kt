package com.priorDev.pokerroutejc.utils

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.ui.Routes

interface GlobalEventChannel {
    fun sendEvent(event: OneTimeEvent)

    fun navigate(
        route: Routes,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    fun navigateUp()
}
