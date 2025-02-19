package com.prior_dev.pokerroutejc.presentation.utils

interface IGlobalEventChannel {
    fun emitGlobalEvent(event: OneTimeEvent)
}