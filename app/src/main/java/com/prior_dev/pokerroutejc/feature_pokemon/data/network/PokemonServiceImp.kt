package com.prior_dev.pokerroutejc.feature_pokemon.data.network

import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonService
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.ContainerPokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.MoveDetailsResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger
import javax.inject.Inject

class PokemonServiceImp @Inject constructor(
    private val api: PokemonApi
): PokemonService {
    override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse?{
        return withContext(Dispatchers.IO){
            api.getAllPokemonsNames(urlLimitOffset).body()
        }
    }

    override suspend fun getPokemon(pokemon: String): PokemonResponse?{
        return withContext(Dispatchers.IO){
            api.getPokemon("pokemon/${pokemon.lowercase()}").body()
        }
    }

    override suspend fun getMoveDetails(move: Long): MoveDetailsResponse?{
        return withContext(Dispatchers.IO){
            api.getMoveDetails("move/$move").body()
        }
    }

}