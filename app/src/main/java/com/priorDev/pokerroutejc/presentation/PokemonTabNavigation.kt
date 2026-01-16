package com.priorDev.pokerroutejc.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.priorDev.pokerroutejc.presentation.pokemonDetails.pokemonDetailWrapper
import com.priorDev.pokerroutejc.presentation.pokemonList.pokemonListWrapper
import com.priorDev.pokerroutejc.presentation.typeDetails.detailsTypeWrapper
import com.priorDev.pokerroutejc.ui.Routes

fun NavGraphBuilder.pokemonNavigation() {
    navigation<Routes.PokemonNav>(
        startDestination = Routes.PokemonList
    ) {
        pokemonListWrapper()

        pokemonDetailWrapper()

        detailsTypeWrapper<Routes.TypeDetails.PokemonTab>()
    }
}
