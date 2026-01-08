package com.priorDev.pokerroutejc.presentation.pokemonSearch

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.ui.Routes
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.pkSearchWrapper() {
    composable<Routes.PkSearch> {
        val searchViewModel = koinViewModel<PkSearchViewModel>()
        val pokemonNames = searchViewModel.pokemonNames

        PkSearchView(
            pokemonNames = pokemonNames,
            onEvent = searchViewModel::onEvent
        )
    }
}
