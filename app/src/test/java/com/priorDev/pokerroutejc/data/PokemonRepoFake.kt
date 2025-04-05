package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.data.network.reponse.evolutionResponse
import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonData
import com.priorDev.pokerroutejc.domain.pokemonData
import com.priorDev.pokerroutejc.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PokemonRepoFake : PokemonRepo {
    var getPokemonFlow = flowOf(ResourceFlow.Success(pokemonData()))
    override suspend fun getPokemon(pokemonName: String): Flow<ResourceFlow<PokemonData>> {
        return getPokemonFlow
    }

    var getPkMoves: Resource<Map<String, List<MoveDetailsData>>> = Resource.Success(emptyMap())
    override suspend fun getPkMoves(
        pokemonId: Int,
        generationName: String,
        language: String
    ): Resource<Map<String, List<MoveDetailsData>>> {
        return getPkMoves
    }

    override suspend fun getAbility(ability: String): Flow<ResourceFlow<AbilityDetailsData>> {
        TODO("Not yet implemented")
    }

    var getEvolutionChain: ResourceFlow<Map<Int?, List<EvolutionResponse>>> = ResourceFlow.Success(
        mapOf(1 to listOf(evolutionResponse()))
    )
    override suspend fun getEvolutionChain(id: Int): ResourceFlow<Map<Int?, List<EvolutionResponse>>> {
        return getEvolutionChain
    }
}
