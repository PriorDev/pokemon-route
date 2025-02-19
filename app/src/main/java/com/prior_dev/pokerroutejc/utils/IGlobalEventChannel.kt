package com.prior_dev.pokerroutejc.utils

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder

interface IGlobalEventChannel {
    fun sendEvent(event: OneTimeEvent)
    fun onNavigate(
        destinationRoute: String,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )
    fun sendNavigateUpEvent()
}