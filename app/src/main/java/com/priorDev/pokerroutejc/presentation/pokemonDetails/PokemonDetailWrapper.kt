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
        val states by viewModel.states.collectAsStateWithLifecycle()
        val pkMovesState by viewModel.pkMovesStates.collectAsStateWithLifecycle()
        val damageRelationStates by viewModel.damageRelationStates.collectAsStateWithLifecycle()
        val evolutionState by viewModel.evolutionState.collectAsStateWithLifecycle()
        val spritesState by viewModel.spritesState.collectAsStateWithLifecycle()
        val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()

        PokemonDetailsScreen(
            states = states,
            pkMovesState = pkMovesState,
            damageRelationStates = damageRelationStates,
            evolutionState = evolutionState,
            spritesState = spritesState,
            selectedLanguage = selectedLanguage,
            movesList = viewModel.moves,
            onEvents = viewModel::onEvent,
        )
    }
}
