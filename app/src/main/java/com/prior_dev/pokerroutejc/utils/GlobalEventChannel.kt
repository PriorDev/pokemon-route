package com.prior_dev.pokerroutejc.utils

import androidx.navigation.NavOptionsBuilder
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

    override fun onNavigate(
        destinationRoute: String,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        coroutineScope.launch {
            _eventChannel.send(
                OneTimeEvent.OnNavigate(
                    destinationRoute = destinationRoute,
                    navOptions = navOptions
                )
            )
        }
    }

    override fun sendNavigateUpEvent() {
        coroutineScope.launch {
            _eventChannel.send(OneTimeEvent.OnNavigateUp)
        }
    }
}

