package com.prior_dev.pokerroutejc.feature_pokemon.data

import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.ContainerPokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.MoveDetailsResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonResponse
import java.math.BigInteger

interface PokemonService {
    suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse?

    suspend fun getPokemon(pokemon: String): PokemonResponse?

    suspend fun getMoveDetails(move: Long): MoveDetailsResponse?
}