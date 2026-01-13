package com.priorDev.pokerroutejc.core.utils

import com.priorDev.pokerroutejc.core.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.core.presentation.components.LoadingIndicator
import com.priorDev.pokerroutejc.core.presentation.UiMessages

sealed class ResourceFlow<T> {
    class Success<T>(val data: T?) : ResourceFlow<T>()

    class Error<T>(
        val uiMessages: UiMessages? = null,
        val data: T? = null,
        val throwable: Throwable? = null,
        val networkErrorType: NetworkError = NetworkError.None
    ) : ResourceFlow<T>()

    class Loading<T>(
        val isLoading: Boolean = true,
        val loadingIndicator: LoadingIndicator = LoadingIndicator.None
    ) : ResourceFlow<T>()
}
