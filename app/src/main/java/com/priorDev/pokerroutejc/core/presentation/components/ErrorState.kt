package com.priorDev.pokerroutejc.core.presentation.components

import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.data.network.utils.NetworkError

data class ErrorState(
    val displayAs: DisplayError = DisplayError.Dialog,
    val networkError: NetworkError = NetworkError.None,
    val actionButtonText: UiMessages = UiMessages.StringResource(R.string.retry),
    val onAction: () -> Unit = {},
    val isActionButtonVisible: Boolean = false,
    val dismissButtonText: UiMessages = UiMessages.StringResource(R.string.dismiss),
    val onDismiss: () -> Unit = {},
    val isDismissButtonVisible: Boolean = false
)

fun NetworkError.retryFullScreen(
    onAction: () -> Unit
): ErrorState {
    return ErrorState(
        displayAs = DisplayError.FullScreen,
        networkError = this,
        actionButtonText = UiMessages.StringResource(R.string.retry),
        onAction = onAction,
        isActionButtonVisible = true,
    )
}
