package com.priorDev.pokerroutejc.data.network

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonNameData

interface IPokemonNameClient {
    suspend fun getPokemonByName(name: String): ResourceFlow<List<PokemonNameData>>
}
