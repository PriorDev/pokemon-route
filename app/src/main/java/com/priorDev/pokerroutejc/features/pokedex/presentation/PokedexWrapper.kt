package com.priorDev.pokerroutejc.features.pokedex.presentation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.navigation.Routes
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.pokedexWrapper() {
    composable<Routes.Pokedex> {
        val viewModel = koinViewModel<PokedexViewModel>()
        val states by viewModel.states.collectAsStateWithLifecycle()

        PokedexScreen(
            states = states,
            onEvent = viewModel::onEvent
        )
    }
}
