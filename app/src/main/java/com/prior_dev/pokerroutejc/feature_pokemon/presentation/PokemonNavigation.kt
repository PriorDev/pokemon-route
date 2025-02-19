package com.prior_dev.pokerroutejc.feature_pokemon.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.prior_dev.pokerroutejc.core.routes.RoutesPokemon
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.details.PokemonDetailsView
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.details.PokemonDetailsViewModel
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.details.pokemonDetailWrapper
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.pokemon_list.pokemonListWrapper
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.search.pkSearchWrapper
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeView
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeViewModel
import com.prior_dev.pokerroutejc.feature_types.presentation.details.detailsTypeWrapper

fun NavGraphBuilder.pokemonNavigation() {
    navigation(
        route = RoutesPokemon.ROUTE_NAME,
        startDestination = RoutesPokemon.PokemonListRoute.route
    ){
        pokemonListWrapper()

        pkSearchWrapper()

        pokemonDetailWrapper()

        detailsTypeWrapper(RoutesPokemon.TypeDetails.route)
    }
}