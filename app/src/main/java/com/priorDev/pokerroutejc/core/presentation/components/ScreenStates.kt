package com.priorDev.pokerroutejc.core.presentation.components

data class ScreenStates(
    val loadingIndicator: LoadingIndicator = LoadingIndicator.None,
    val error: ErrorState? = null,
    val dialogModel: AlertDialogModel = AlertDialogModel()
)
