package com.prior_dev.pokerroutejc.utils

import androidx.navigation.NavOptionsBuilder

sealed interface OneTimeEvent {
    class OnNavigate(
        val destinationRoute: String,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : OneTimeEvent

    object OnNavigateUp : OneTimeEvent
}