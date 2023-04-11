package com.prior_dev.pokemonrroutejc.feature_pokemon.data.network

import com.prior_dev.pokemonrroutejc.feature_pokemon.data.network.response.ContainerPokemonNameResponse
import com.prior_dev.pokemonrroutejc.feature_pokemon.data.network.response.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonApi {
    @GET
    suspend fun getAllPokemonsNames(@Url urlLimitOffset: String):
            Response<ContainerPokemonNameResponse>

    @GET
    suspend fun getPokemon(@Url urlPokemon: String): Response<PokemonResponse>
}