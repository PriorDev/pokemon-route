package com.priorDev.pokerroutejc.presentation.core

data class ScreenStates(
    val loadingIndicator: LoadingIndicator = LoadingIndicator.None,
    val error: ErrorState = ErrorState(),
    val dialogModel: AlertDialogModel = AlertDialogModel()
)
