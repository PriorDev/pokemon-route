package com.priorDev.pokerroutejc.data.network

data class NetworkRequestData(
    val url: String,
    val params: Map<String, String> = emptyMap(),
    val segments: List<String> = emptyList()
)
