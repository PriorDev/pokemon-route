package com.priorDev.pokerroutejc.data.network

import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.UiMessages

sealed class NetworkError(
    val userFriendlyMessage: UiMessages? = null
) {
    data object ClientError : NetworkError()
    data class ServerError(
        val serverMessage: String
    ) : NetworkError()
    data object UnknownError : NetworkError(
        UiMessages.StringResource(R.string.unknown_error)
    )
    data object UnableToConnect : NetworkError(
        UiMessages.StringResource(R.string.unable_to_connect_error)
    )
    data object EmptyContent : NetworkError()
}
