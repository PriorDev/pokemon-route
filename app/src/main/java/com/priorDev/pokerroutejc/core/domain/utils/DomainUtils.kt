package com.priorDev.pokerroutejc.core.domain.utils

fun String.romanToDecimal(): Int {
    var result = 0
    var prevValue = 0

    for (i in length - 1 downTo 0) {
        val value = when (this[i]) {
            'I' -> 1
            'V' -> 5
            'X' -> 10
            'L' -> 50
            'C' -> 100
            'D' -> 500
            'M' -> 1000
            else -> 0
        }

        if (value < prevValue) {
            result -= value
        } else {
            result += value
        }
        prevValue = value
    }
    return result
}
