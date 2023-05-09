package com.prior_dev.pokerroutejc.feature_pokemon.data.network

import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.ContainerPokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(
    private val api: PokemonApi
) {
    suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse?{
        return withContext(Dispatchers.IO){
            api.getAllPokemonsNames(urlLimitOffset).body()
        }
    }

    suspend fun getPokemon(pokemon: String): PokemonResponse?{
        return withContext(Dispatchers.IO){
            api.getPokemon("pokemon/${pokemon.lowercase()}").body()
        }
    }
}