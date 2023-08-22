package com.prior_dev.pokerroutejc.core

import java.math.BigInteger

fun String.getTypeColor() =
    EnumColorTypes.values()
        .firstOrNull{ it.type == this }?.color ?: EnumColorTypes.Normal.color


fun String.getIdFromPokeUrl(): Int{
    return this.substring(0, this.length - 1).split("/").last().toInt()
}

fun String.getBigIdFromPokeUrl(): BigInteger{
    return this.substring(0, this.length - 1).split("/").last().toBigInteger()
}
