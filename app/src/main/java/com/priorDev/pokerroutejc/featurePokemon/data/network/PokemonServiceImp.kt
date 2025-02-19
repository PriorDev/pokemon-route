package com.priorDev.pokerroutejc.featurePokemon.data.network

import com.priorDev.pokerroutejc.featurePokemon.data.PokemonService
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.AbilityDetailsResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.ContainerPokemonNameResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.MoveDetailsResponse
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.PokemonResponse
import javax.inject.Inject

class PokemonServiceImp @Inject constructor(
    private val api: PokemonApi
) : PokemonService {
    override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
        return api.getAllPokemonsNames(urlLimitOffset).body()
    }

    override suspend fun getPokemon(pokemon: String): PokemonResponse? {
        return api.getPokemon("pokemon/${pokemon.lowercase()}").body()
    }

    override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
        return api.getMoveDetails("move/$move").body()
    }

    override suspend fun getAbility(abilty: String): AbilityDetailsResponse? {
        return api.getAbility("ability/${abilty.lowercase()}").body()
    }
}
