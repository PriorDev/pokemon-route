package com.priorDev.pokerroutejc.featurePokemon.data

import com.priorDev.pokerroutejc.featurePokemon.data.network.response.AbilityDetailsResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.ContainerPokemonNameResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.MoveDetailsResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.PokemonResponse

interface PokemonService {
    suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse?

    suspend fun getPokemon(pokemon: String): PokemonResponse?

    suspend fun getMoveDetails(move: Long): MoveDetailsResponse?

    suspend fun getAbility(abilty: String): AbilityDetailsResponse?
}
