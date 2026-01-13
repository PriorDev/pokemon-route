package com.priorDev.pokerroutejc.core.data

import com.priorDev.pokerroutejc.core.utils.Resource
import com.priorDev.pokerroutejc.core.utils.ResourceFlow
import com.priorDev.pokerroutejc.core.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.core.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.core.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.core.domain.pokemon.models.PokemonData
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
