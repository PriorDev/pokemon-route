package com.priorDev.pokerroutejc.data.network.pokemon

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonNameData

interface PokemonApolloService {
    suspend fun getPokemonByName(name: String): ResourceFlow<List<PokemonNameData>>
    suspend fun getEvolutionChain(pokemonId: Int): ResourceFlow<Map<Int?, List<EvolutionResponse>>>
}
