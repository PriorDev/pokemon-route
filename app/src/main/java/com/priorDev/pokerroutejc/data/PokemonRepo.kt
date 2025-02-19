package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.Resource
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveData
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonData
import kotlinx.coroutines.flow.Flow

interface PokemonRepo {
    suspend fun getPokemon(pokemonName: String): Flow<ResourceFlow<PokemonData>>

    suspend fun getPkMoves(
        pokemonId: Int,
        generationName: String,
        language: String
    ): Resource<Map<String, List<MoveDetailsData>>>

    suspend fun getAbility(ability: String): Flow<ResourceFlow<AbilityDetailsData>>

    suspend fun getEvolutionChain(id: Int): ResourceFlow<Map<Int?, List<EvolutionResponse>>>
}
