package com.priorDev.pokerroutejc.utils

import kotlinx.coroutines.flow.MutableSharedFlow

class FlowSource<T> {
    val flow = MutableSharedFlow<T>()

    suspend fun emit(value: T) {
        flow.emit(value)
    }
}
