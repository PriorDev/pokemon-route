package com.priorDev.pokerroutejc.core.utils

enum class ApiLanguages(val key: String) {
    SPANISH("es"),
    ENGLISH("en")
}

fun String?.toEnumLanguage(): ApiLanguages {
    return ApiLanguages.entries.firstOrNull { it.key == this }
        ?: ApiLanguages.ENGLISH
}
