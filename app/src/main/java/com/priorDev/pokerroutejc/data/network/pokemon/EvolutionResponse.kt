package com.priorDev.pokerroutejc.data.network.pokemon

import com.priorDev.GetEvolutionChainIdQuery

data class EvolutionResponse(
    val specieId: Int,
    val name: String,
    val evolvesFromSpecieId: Int?,
    val evolutionTriggerResponse: List<EvolutionTriggerResponse>
)

fun GetEvolutionChainIdQuery.Pokemon_v2_pokemonspecy1
    .toResponse(): EvolutionResponse {
    return EvolutionResponse(
        specieId = id,
        name = name,
        evolvesFromSpecieId = evolves_from_species_id,
        evolutionTriggerResponse = pokemon_v2_pokemonevolutions.map { trigger ->
            EvolutionTriggerResponse(
                minLevel = trigger.min_level,
                minHappiness = trigger.min_happiness,
                minAffection = trigger.min_affection,
                needsOverworldRain = trigger.needs_overworld_rain,
                timeOfDay = trigger.time_of_day,
                tradeSpeciesId = trigger.trade_species_id,
                item = trigger.pokemon_v2_item?.name,
                move = trigger.pokemon_v2_move?.name,
                location = trigger.pokemon_v2_location?.name
            )
        }
    )
}