package com.prior_dev.pokerroutejc.presentation.utils

sealed interface OneTimeEvent {
    object OnNavigate: OneTimeEvent
}