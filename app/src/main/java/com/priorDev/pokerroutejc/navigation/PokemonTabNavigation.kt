package com.priorDev.pokerroutejc.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.priorDev.pokerroutejc.features.pokemon_details.presentation.pokemonDetailWrapper
import com.priorDev.pokerroutejc.features.pokemon_list.presentation.pokemonListWrapper
import com.priorDev.pokerroutejc.features.pokemon_search.presentation.pkSearchWrapper
import com.priorDev.pokerroutejc.features.types_details.presentation.detailsTypeWrapper
import com.priorDev.pokerroutejc.navigation.Routes

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
