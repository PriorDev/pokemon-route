package com.priorDev.pokerroutejc.presentation.core

import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.data.network.NetworkError

data class ErrorState (
    val displayAs: DisplayError = DisplayError.Dialog,
    val networkError: NetworkError = NetworkError.None,
    val actionButtonText: UiMessages = UiMessages.StringResource(R.string.retry),
    val onAction: () -> Unit = {},
    val isActionButtonVisible: Boolean = false,
    val dismissButtonText: UiMessages = UiMessages.StringResource(R.string.dismiss),
    val onDismiss: () -> Unit = {},
    val isDismissButtonVisible: Boolean = false
)
