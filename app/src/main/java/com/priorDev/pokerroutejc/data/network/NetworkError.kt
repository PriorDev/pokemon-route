package com.priorDev.pokerroutejc.data.network

import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.presentation.core.UiMessages

sealed class NetworkError(
    val userFriendlyMessage: UiMessages
) {
    data class ClientError(
        val serverMessage: String? = null
    ) : NetworkError(
        UiMessages.StringResource(R.string.client_error)
    )

    data class ServerError(
        val serverMessage: String
    ) : NetworkError(
        UiMessages.StringResource(R.string.server_error)
    )

    data object UnknownError : NetworkError(
        UiMessages.StringResource(R.string.unknown_error)
    )

    data class UnableToConnect(
        val showRetryButton: Boolean = false,
        val retryAction: (() -> Unit)? = null,
        val retryButtonText: UiMessages = UiMessages.StringResource(R.string.error_retry),
        val showOfflineDataButton: Boolean = false,
        val showOfflineDataAction: (() -> Unit)? = null,
        val showOfflineDataButtonText: UiMessages = UiMessages.StringResource(R.string.use_offline_data),
    ) : NetworkError(
        UiMessages.StringResource(R.string.unable_to_connect_error)
    )

    data object EmptyContent : NetworkError(
        UiMessages.StringResource(R.string.empty_content)
    )

    data object None : NetworkError(
        UiMessages.DynamicMessage("")
    )
}
