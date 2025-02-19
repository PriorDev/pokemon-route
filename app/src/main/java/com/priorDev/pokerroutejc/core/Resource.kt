package com.priorDev.pokerroutejc.core

import com.priorDev.pokerroutejc.data.network.NetworkError
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.UiMessages

sealed class Resource<T> {
    class Success<T>(val data: T?) : Resource<T>()

    class Error<T>(
        val uiMessages: UiMessages? = null,
        val data: T? = null,
        val throwable: Throwable? = null,
        val networkErrorType: NetworkError = NetworkError.None
    ) : Resource<T>()

    class Loading<T>(
        val isLoading: Boolean = true,
        val loadingIndicator: LoadingIndicator = LoadingIndicator.None
    ) : Resource<T>()
}
