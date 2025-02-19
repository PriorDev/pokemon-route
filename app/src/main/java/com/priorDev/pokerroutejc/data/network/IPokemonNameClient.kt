package com.priorDev.pokerroutejc.data.network

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.pokemon.EvolutionResponse
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonNameData

interface IPokemonNameClient {
    suspend fun getPokemonByName(name: String): ResourceFlow<List<PokemonNameData>>
    suspend fun getEvolutionChain(pokemonId: Int): ResourceFlow<Map<Int?, List<EvolutionResponse>>>
}
