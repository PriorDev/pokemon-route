package com.priorDev.pokerroutejc.utils

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.ui.Routes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface GlobalEventChannel {
    val eventChannel: Flow<OneTimeEvent>

    fun sendEvent(event: OneTimeEvent)

    fun navigate(
        route: Routes,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    fun navigateUp()
}
