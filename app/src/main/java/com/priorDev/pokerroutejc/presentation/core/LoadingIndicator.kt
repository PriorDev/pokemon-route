package com.priorDev.pokerroutejc.presentation.core

sealed class LoadingIndicator {
    data object None : LoadingIndicator()
    data object TopLinear : LoadingIndicator()
    data object SolidSpinningWheel : LoadingIndicator()
    data object Refreshing : LoadingIndicator()
}

fun spinningWheelOrRefresh(isRefreshing: Boolean): LoadingIndicator {
    return if (isRefreshing) {
        LoadingIndicator.Refreshing
    } else {
        LoadingIndicator.SolidSpinningWheel
    }
}
