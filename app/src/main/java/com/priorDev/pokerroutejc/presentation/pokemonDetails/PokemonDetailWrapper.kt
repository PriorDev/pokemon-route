package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.ui.Routes
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.pokemonDetailWrapper() {
    composable<Routes.PkDetails> {
        val viewModel = koinViewModel<PokemonDetailsViewModel>()
        val pkMovesState by viewModel.pkMovesStates.collectAsStateWithLifecycle()
        val damageRelationStates by viewModel.damageRelationStates.collectAsStateWithLifecycle()
        val states = viewModel.states.collectAsStateWithLifecycle()
        val selectedLanguage = viewModel.selectedLanguage.collectAsStateWithLifecycle()

        PokemonDetailsScreen(
            pkMovesState = pkMovesState,
            damageRelationStates = damageRelationStates,
            states = states.value,
            selectedLanguage = selectedLanguage.value,
            movesList = viewModel.moves,
            onEvents = viewModel::onEvent,
        )
    }
}
