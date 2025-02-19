package com.priorDev.pokerroutejc.domain

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.utils.OneTimeEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class GlobalEventChannelFake: GlobalEventChannel {
    override val eventChannel: Flow<OneTimeEvent>
        get() = listOf(
            OneTimeEvent.OnNavigateUp
        ).asFlow()

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