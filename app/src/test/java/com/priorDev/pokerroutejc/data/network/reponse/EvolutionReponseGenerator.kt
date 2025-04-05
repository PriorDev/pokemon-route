package com.priorDev.pokerroutejc.data.network.reponse

import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionTriggerResponse

fun evolutionResponse(): EvolutionResponse {
    return EvolutionResponse(
        name = "",
        specieId = 1,
        evolvesFromSpecieId = 1,
        evolutionTriggerResponse = listOf(
            EvolutionTriggerResponse(
                minLevel = null,
                minHappiness = null,
                minAffection = 1,
                needsOverworldRain = false,
                timeOfDay = null,
                tradeSpeciesId = null,
                item = "",
                move = "",
                location = ""
            )
        )
    )
}
