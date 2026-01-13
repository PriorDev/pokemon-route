package com.priorDev.pokerroutejc.core.utils

import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.presentation.EnumColorTypes
import com.priorDev.pokerroutejc.core.presentation.UiMessages

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

fun Float.getDamageTitle(): UiMessages {
    val stringId = when (this) {
        4f -> {
            R.string.super_weaknesses
        }

        2f -> {
            R.string.weak
        }

        0.5f -> {
            R.string.resist
        }

        0.25f -> {
            R.string.super_resist
        }

        0f -> {
            R.string.immune
        }

        else -> {
            R.string.relation
        }
    }

    return UiMessages.StringResource(stringId, this)
}
