package com.priorDev.pokerroutejc.presentation.core

sealed class LoadingIndicator {
    data object None : LoadingIndicator()
    data object TopLinear : LoadingIndicator()
    data object SpinningWheel : LoadingIndicator()
    data object Refreshing : LoadingIndicator()
}