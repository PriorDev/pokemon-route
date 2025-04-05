package com.priorDev.pokerroutejc.utils

import com.priorDev.pokerroutejc.data.network.utils.NetworkError


sealed class Resource<T> {
    class Success<T>(val data: T?) : Resource<T>()

    class Error<T>(
        val throwable: Throwable? = null,
        val networkErrorType: NetworkError = NetworkError.None
    ) : Resource<T>()
}
