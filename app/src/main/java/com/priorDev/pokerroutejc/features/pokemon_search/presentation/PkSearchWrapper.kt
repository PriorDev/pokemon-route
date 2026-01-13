package com.priorDev.pokerroutejc.features.pokemon_search.presentation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.navigation.Routes
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.pkSearchWrapper() {
    composable<Routes.PkSearch> {
        val searchViewModel = koinViewModel<PkSearchViewModel>()
        val states by searchViewModel.states.collectAsStateWithLifecycle()

        PkSearchScreen(
            states = states,
            onEvent = searchViewModel::onEvent
        )
    }
}
