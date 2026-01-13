package com.priorDev.pokerroutejc.core.utils

import com.priorDev.pokerroutejc.core.data.network.utils.NetworkError

sealed class Resource<T> {
    class Success<T>(val data: T?) : Resource<T>()

    class Error<T>(
        val throwable: Throwable? = null,
        val networkErrorType: NetworkError = NetworkError.None
    ) : Resource<T>()
}
