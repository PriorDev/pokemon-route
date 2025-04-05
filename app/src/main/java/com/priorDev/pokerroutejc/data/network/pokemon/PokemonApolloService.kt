package com.priorDev.pokerroutejc.data.network.pokemon

import com.priorDev.pokerroutejc.utils.Resource
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonNameData

interface PokemonApolloService {
    suspend fun getPokemonByName(name: String): ResourceFlow<List<PokemonNameData>>

    suspend fun getEvolutionChain(pokemonId: Int): ResourceFlow<Map<Int?, List<EvolutionResponse>>>

    suspend fun getPkMoves(
        pokemonId: Int,
        generationName: String,
        language: String
    ): Resource<Map<String, List<MoveDetailsData>>>
}
