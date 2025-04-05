package com.priorDev.pokerroutejc.core

fun Int.getTypeColor() =
    EnumColorTypes.entries
        .firstOrNull { it.typeId == this }?.color
        ?: EnumColorTypes.Normal.color

fun String.getIdFromPokeUrl(): Int {
    return this.substring(0, this.length - 1).split("/").last().toInt()
}

fun String.getLongIdFromPokeUrl(): Long {
    return this.substring(0, this.length - 1).split("/").last().toLong()
}

fun String?.orDefault(value: String): String {
    return this ?: value
}
