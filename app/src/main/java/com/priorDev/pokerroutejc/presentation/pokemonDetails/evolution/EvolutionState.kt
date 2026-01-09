package com.priorDev.pokerroutejc.presentation.pokemonDetails.evolution

import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse

data class EvolutionState(
    val evolutions: Map<Int, List<EvolutionResponse>> = emptyMap()
)
