package com.priorDev.pokerroutejc.core.utils

fun Int?.orZero() = this ?: 0

fun String.capitalized(): String {
    return this.replaceFirstChar {
        it.uppercase()
    }
}
