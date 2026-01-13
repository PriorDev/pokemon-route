package com.priorDev.pokerroutejc.core.data.network.pokemon.responses

data class EvolutionTriggerResponse(
    val minLevel: Int?,
    val minHappiness: Int?,
    val minAffection: Int?,
    val needsOverworldRain: Boolean,
    val timeOfDay: String?,
    val tradeSpeciesId: Int?,
    val item: String?,
    val move: String?,
    val location: String?
)
