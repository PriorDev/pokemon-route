package com.priorDev.pokerroutejc.featurePokemon.data

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.featurePokemon.domain.AbilityDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.MoveData
import com.priorDev.pokerroutejc.featurePokemon.domain.MoveDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonData
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemon(pokemonName: String): Flow<ResourceFlow<PokemonData>>

    suspend fun getMoveDetails(moves: List<MoveData>): Flow<ResourceFlow<MoveDetailsData>>

    suspend fun getAbility(ability: String): Flow<ResourceFlow<AbilityDetailsData>>

    suspend fun getEvolutionChain(id: Int): ResourceFlow<Map<Int?, List<EvolutionResponse>>>
}
