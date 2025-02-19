package com.priorDev.pokerroutejc.featurePokemon.data.network

import com.priorDev.pokerroutejc.core.Resource
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonNameData

interface IPokemonNameClient {
    suspend fun getPokemonByName(name: String): Resource<List<PokemonNameData>>
}