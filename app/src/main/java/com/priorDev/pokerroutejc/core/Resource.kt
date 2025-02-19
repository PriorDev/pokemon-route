package com.priorDev.pokerroutejc.core

import com.priorDev.pokerroutejc.data.network.NetworkError

sealed class Resource<T> {
    class Success<T>(val data: T?) : Resource<T>()

    class Error<T>(
        val uiMessages: UiMessages? = null,
        val data: T? = null,
        val throwable: Throwable? = null,
        val networkErrorType: NetworkError? = null
    ) : Resource<T>()

    class Loading<T>(val isLoading: Boolean = true) : Resource<T>()
}
