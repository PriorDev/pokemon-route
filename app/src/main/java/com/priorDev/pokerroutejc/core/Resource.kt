package com.priorDev.pokerroutejc.core

sealed class Resource<T> {
    class Success<T>(val data: T?) : Resource<T>()
    class Error<T>(
        val uiMessages: UiMessages,
        val data: T? = null,
        val throwable: Throwable
    ) : Resource<T>()
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>()
}
