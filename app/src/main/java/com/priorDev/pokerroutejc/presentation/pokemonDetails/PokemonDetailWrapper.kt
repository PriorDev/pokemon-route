package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.ui.Routes

fun NavGraphBuilder.pokemonDetailWrapper() {
    composable<Routes.PkDetails> {
        val viewModel = hiltViewModel<PokemonDetailsViewModel>()
        val commonStates = viewModel.commonStates.collectAsStateWithLifecycle()
        val states = viewModel.states.collectAsStateWithLifecycle()
        val moveList = viewModel.moves

        PokemonDetailsScreen(
            commonStates = commonStates.value,
            states = states.value,
            movesList = moveList,
            onEvents = viewModel::onEvent,
        )
    }
}
