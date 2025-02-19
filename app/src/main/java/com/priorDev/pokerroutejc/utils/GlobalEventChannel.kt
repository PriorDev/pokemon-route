package com.priorDev.pokerroutejc.utils

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.ui.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

object GlobalEventChannel : IGlobalEventChannel {
    private val _eventChannel = Channel<OneTimeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun sendEvent(event: OneTimeEvent) {
        coroutineScope.launch {
            _eventChannel.send(event)
        }
    }

    override fun navigate(
        route: Routes,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        coroutineScope.launch {
            _eventChannel.send(
                OneTimeEvent.OnNavigate(
                    route = route,
                    navOptions = navOptions
                )
            )
        }
    }

    override fun navigateUp() {
        coroutineScope.launch {
            _eventChannel.send(OneTimeEvent.OnNavigateUp)
        }
    }
}
