package com.prior_dev.pokerroutejc.core

fun String.getTypeColor() =
    EnumColorTypes.values()
        .firstOrNull{ it.type == this }?.color ?: EnumColorTypes.Normal.color

//TODO:Add a trycath block
fun String.getIdFromPokeUrl(): Int{
    return this.substring(0, this.length - 1).split("/").last().toInt()
}

fun String.getLongIdFromPokeUrl(): Long{
    return this.substring(0, this.length - 1).split("/").last().toLong()
}
