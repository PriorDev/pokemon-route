package com.priorDev.pokerroutejc.utils

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.ui.Routes
import kotlinx.coroutines.flow.Flow

class GlobalEventChannelFake : GlobalEventChannel {
    override val eventChannel: Flow<OneTimeEvent>
        get() = TODO("Not yet implemented")

    override fun sendEvent(event: OneTimeEvent) {
        TODO("Not yet implemented")
    }

    override fun navigate(route: Routes, navOptions: NavOptionsBuilder.() -> Unit) {
        TODO("Not yet implemented")
    }

    override fun navigateUp() {
        TODO("Not yet implemented")
    }
}
