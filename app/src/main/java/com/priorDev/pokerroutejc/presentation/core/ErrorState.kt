package com.priorDev.pokerroutejc.presentation.core

import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.data.network.utils.NetworkError

data class ErrorState<E>(
    val displayAs: DisplayError = DisplayError.Dialog,
    val networkError: NetworkError = NetworkError.None,
    val actionButtonText: UiMessages = UiMessages.StringResource(R.string.retry),
    val actionEvent: E? = null,
    val isActionButtonVisible: Boolean = false,
    val dismissButtonText: UiMessages = UiMessages.StringResource(R.string.dismiss),
    val dismissEvent: E? = null,
    val isDismissButtonVisible: Boolean = false
)

fun <E> NetworkError.retryFullScreen(
    actionEvent: E
): ErrorState<E> {
    return ErrorState(
        displayAs = DisplayError.FullScreen,
        networkError = this,
        actionButtonText = UiMessages.StringResource(R.string.retry),
        actionEvent = actionEvent,
        isActionButtonVisible = true,
    )
}
