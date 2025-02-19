package com.prior_dev.pokerroutejc.presentation.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

object GlobalEventChannel : IGlobalEventChannel {
    private val _eventChannel = Channel<OneTimeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun emitGlobalEvent(event: OneTimeEvent) {
        coroutineScope.launch {
            _eventChannel.send(event)
        }
    }
}

