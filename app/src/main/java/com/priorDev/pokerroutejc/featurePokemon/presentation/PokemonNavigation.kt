package com.priorDev.pokerroutejc.featurePokemon.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.priorDev.pokerroutejc.core.routes.RoutesPokemon
import com.priorDev.pokerroutejc.featurePokemon.presentation.details.pokemonDetailWrapper
import com.priorDev.pokerroutejc.featurePokemon.presentation.pokemonList.pokemonListWrapper
import com.priorDev.pokerroutejc.featurePokemon.presentation.search.pkSearchWrapper
import com.priorDev.pokerroutejc.featureTypes.presentation.details.detailsTypeWrapper

fun NavGraphBuilder.pokemonNavigation() {
    navigation(
        route = RoutesPokemon.ROUTE_NAME,
        startDestination = RoutesPokemon.PokemonListRoute.route
    ) {
        pokemonListWrapper()

        pkSearchWrapper()

        pokemonDetailWrapper()

        detailsTypeWrapper(RoutesPokemon.TypeDetails.route)
    }
}
