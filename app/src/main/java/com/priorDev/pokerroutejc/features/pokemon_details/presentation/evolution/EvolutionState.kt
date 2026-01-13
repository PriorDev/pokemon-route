package com.priorDev.pokerroutejc.features.pokemon_details.presentation.evolution

import com.priorDev.pokerroutejc.core.data.network.pokemon.responses.EvolutionResponse

data class EvolutionState(
    val evolutions: Map<Int?, List<EvolutionResponse>> = emptyMap()
)
