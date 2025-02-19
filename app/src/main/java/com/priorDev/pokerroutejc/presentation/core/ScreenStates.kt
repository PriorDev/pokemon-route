package com.priorDev.pokerroutejc.presentation.core

import com.priorDev.pokerroutejc.data.network.NetworkError

data class ScreenStates(
    val loadingIndicator: LoadingIndicator = LoadingIndicator.None,
    val networkError: NetworkError = NetworkError.None,
    val dialogModel: AlertDialogModel = AlertDialogModel()
)
