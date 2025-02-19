package com.priorDev.pokerroutejc.featurePokemon.data

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.featurePokemon.domain.AbilityDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.MoveData
import com.priorDev.pokerroutejc.featurePokemon.domain.MoveDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonData
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonNameData
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun searchPokemonNameByMatch(name: String): Flow<ResourceFlow<List<PokemonNameData>>>

    suspend fun getPokemon(pokemonName: String): Flow<ResourceFlow<PokemonData>>

    suspend fun getPokemonNamePaging(offSet: Int): Flow<ResourceFlow<List<PokemonNameData>>>

    suspend fun getMoveDetails(moves: List<MoveData>): Flow<ResourceFlow<MoveDetailsData>>

    suspend fun getAbility(ability: String): Flow<ResourceFlow<AbilityDetailsData>>
}
