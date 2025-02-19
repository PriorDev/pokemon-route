package com.priorDev.pokerroutejc.featurePokemon.presentation.details

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.priorDev.pokerroutejc.core.routes.RoutesPokemon

fun NavGraphBuilder.pokemonDetailWrapper() {
    composable(
        route = RoutesPokemon.PokemonDetails.route,
        arguments = listOf(
            navArgument(RoutesPokemon.PokemonDetails.argPokemonName) {
                type = NavType.StringType
            }
        )
    ) {
        val viewModel = hiltViewModel<PokemonDetailsViewModel>()
        val commonStates = viewModel.commonStates.collectAsStateWithLifecycle()
        val states = viewModel.states.collectAsStateWithLifecycle()
        val moveList = viewModel.moves

        PokemonDetailsView(
            commonStates = commonStates.value,
            states = states.value,
            movesList = moveList,
            onEvents = viewModel::onEvent,
        )
    }
}
