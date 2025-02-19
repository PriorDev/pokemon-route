package com.priorDev.pokerroutejc.data.network.utils

import io.ktor.util.reflect.TypeInfo

data class NetworkRequestData(
    val url: String,
    val params: Map<String, String> = emptyMap(),
    val segments: List<String> = emptyList(),
    val typeInfo: TypeInfo
)
