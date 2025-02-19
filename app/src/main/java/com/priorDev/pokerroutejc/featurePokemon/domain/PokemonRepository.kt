package com.priorDev.pokerroutejc.featurePokemon.domain

import com.priorDev.pokerroutejc.core.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun searchPokemonNameByMatch(name: String): Flow<Resource<List<PokemonNameData>>>

    suspend fun getPokemon(pokemonName: String): Flow<Resource<PokemonData>>

    suspend fun getPokemonNamePaging(offSet: Int): Flow<Resource<List<PokemonNameData>>>

    suspend fun getMoveDetails(moves: List<MoveData>): Flow<Resource<MoveDetailsData>>

    suspend fun getAbility(ability: String): Flow<Resource<AbilityDetailsData>>
}
