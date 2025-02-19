package com.prior_dev.pokerroutejc.presentation.utils

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder

interface IGlobalEventChannel {
    fun sendEvent(event: OneTimeEvent)
    fun sendNavigateEvent(
        destinationRoute: String,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )
    fun sendNavigateUpEvent()
}