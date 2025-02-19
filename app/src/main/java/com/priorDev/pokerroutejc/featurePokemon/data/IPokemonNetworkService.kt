package com.priorDev.pokerroutejc.featurePokemon.data

import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.AbilityDetailsResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.ContainerPokemonNameResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.MoveDetailsResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.PokemonResponse

interface IPokemonNetworkService {
    suspend fun getAllPokemons(
        offset: Int,
        limit: Int
    ): NetworkResource<ContainerPokemonNameResponse>

    suspend fun getPokemon(pokemon: String): NetworkResource<PokemonResponse>

    suspend fun getMoveDetails(move: String): NetworkResource<MoveDetailsResponse>

    suspend fun getAbility(ability: String): NetworkResource<AbilityDetailsResponse>
}
