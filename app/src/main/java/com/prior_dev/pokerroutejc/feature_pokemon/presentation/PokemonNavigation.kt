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
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.pokemon_list.pokemonListWrapper
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.search.pkSearchWrapper
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeView
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeViewModel

fun NavGraphBuilder.pokemonNavigation() {
    navigation(
        route = RoutesPokemon.ROUTE_NAME,
        startDestination = RoutesPokemon.PokemonListRoute.route
    ){
        pokemonListWrapper()

        pkSearchWrapper()

        composable(
            route = RoutesPokemon.PokemonDetails.route,
            arguments = listOf(
                navArgument(RoutesPokemon.PokemonDetails.argPokemonName){
                    type = NavType.StringType
                }
            )
        ){
            val viewModel = hiltViewModel<PokemonDetailsViewModel>()
            val commonStates = viewModel.commonStates.collectAsStateWithLifecycle()
            val states = viewModel.states.collectAsStateWithLifecycle()
            val moveList = viewModel.moves

            PokemonDetailsView(
                commonStates = commonStates.value,
                states = states.value,
                movesList =  moveList,
                onEvents = viewModel::onEvent,
            )
        }

        composable(
            route = RoutesPokemon.TypeDetails.route,
            arguments = listOf(
                navArgument(name = RoutesPokemon.TypeDetails.argType){
                    type = NavType.IntType
                }
            )
        ){
            val viewModel: DetailsTypeViewModel = hiltViewModel()
            val states = viewModel.states.collectAsStateWithLifecycle()
            val details = viewModel.details.collectAsStateWithLifecycle()
            DetailsTypeView(
                states = states.value,
                details = details.value,
                onEvents = viewModel::onEvent
            )
        }
    }
}