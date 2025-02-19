package com.priorDev.pokerroutejc.data.network.pokemon

import com.priorDev.pokerroutejc.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.data.network.pokemon.responses.AbilityDetailsResponse
import com.priorDev.pokerroutejc.data.network.pokemon.responses.ContainerPokemonNameResponse
import com.priorDev.pokerroutejc.data.network.pokemon.responses.MoveDetailsResponse
import com.priorDev.pokerroutejc.data.network.pokemon.responses.PokemonResponse

interface PokemonNetService {
    suspend fun getAllPokemons(
        offset: Int,
        limit: Int
    ): NetworkResource<ContainerPokemonNameResponse>

    suspend fun getPokemon(pokemon: String): NetworkResource<PokemonResponse>

    suspend fun getMoveDetails(move: String): NetworkResource<MoveDetailsResponse>

    suspend fun getAbility(ability: String): NetworkResource<AbilityDetailsResponse>
}
