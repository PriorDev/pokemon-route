package com.priorDev.pokerroutejc.core.utils

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.navigation.Routes
import kotlinx.coroutines.flow.Flow

interface GlobalEventChannel {
    val eventChannel: Flow<OneTimeEvent>

    fun sendEvent(event: OneTimeEvent)

    fun navigate(
        route: Routes,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    fun navigateUp()
}
