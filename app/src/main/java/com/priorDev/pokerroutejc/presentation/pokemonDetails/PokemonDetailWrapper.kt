package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.ui.Routes
import org.koin.androidx.compose.getViewModel

fun NavGraphBuilder.pokemonDetailWrapper() {
    composable<Routes.PkDetails> {
        val viewModel = getViewModel<PokemonDetailsViewModel>()
        val pkMovesState = viewModel.pkMovesStates.collectAsStateWithLifecycle()
        val states = viewModel.states.collectAsStateWithLifecycle()
        val moveList = viewModel.moves

        PokemonDetailsScreen(
            pkMovesState = pkMovesState.value,
            states = states.value,
            movesList = moveList,
            onEvents = viewModel::onEvent,
        )
    }
}
