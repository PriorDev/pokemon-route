package com.priorDev.pokerroutejc.featurePokemon.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.priorDev.pokerroutejc.featurePokemon.presentation.details.pokemonDetailWrapper
import com.priorDev.pokerroutejc.featurePokemon.presentation.pokemonList.pokemonListWrapper
import com.priorDev.pokerroutejc.featurePokemon.presentation.search.pkSearchWrapper
import com.priorDev.pokerroutejc.featureTypes.presentation.details.detailsTypeWrapper
import com.priorDev.pokerroutejc.utils.Routes

fun NavGraphBuilder.pokemonNavigation() {
    navigation<Routes.PokemonNav>(
        startDestination = Routes.PokemonList
    ) {
        pokemonListWrapper()

        pkSearchWrapper()

        pokemonDetailWrapper()

        detailsTypeWrapper<Routes.TypeDetails.PokemonTab>()
    }
}
