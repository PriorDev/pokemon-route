package com.prior_dev.pokerroutejc.feature_pokemon.domain

import com.prior_dev.pokerroutejc.core.Resource
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun searchPokemonNameByMatch(name: String): Flow<Resource<List<PokemonNameData>>>

    suspend fun getListOfPokemon(
        pokemonsNames: List<PokemonNameData>
    ): Flow<Resource<List<PokemonData>>>

    suspend fun getPokemon(pokemonName: String): Flow<Resource<PokemonData>>

    suspend fun getPokemonNamePaging(offSet: Int): Flow<Resource<List<PokemonNameData>>>
}