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
        val selectedLanguage = viewModel.selectedLanguage.collectAsStateWithLifecycle()
        val moveList = viewModel.moves

        PokemonDetailsScreen(
            pkMovesState = pkMovesState.value,
            states = states.value,
            selectedLanguage = selectedLanguage.value,
            movesList = moveList,
            onEvents = viewModel::onEvent,
        )
    }
}
