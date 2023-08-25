package com.prior_dev.pokerroutejc.feature_pokemon.data.network

import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.AbilityDetailsResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.ContainerPokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.MoveDetailsResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonApi {
    @GET
    suspend fun getAllPokemonsNames(@Url urlLimitOffset: String):
            Response<ContainerPokemonNameResponse>

    @GET
    suspend fun getPokemon(@Url pokemonUrl: String): Response<PokemonResponse>

    @GET
    suspend fun getMoveDetails(@Url moveUrl: String): Response<MoveDetailsResponse>

    @GET
    suspend fun getAbility(@Url abilityUrl: String): Response<AbilityDetailsResponse>
}