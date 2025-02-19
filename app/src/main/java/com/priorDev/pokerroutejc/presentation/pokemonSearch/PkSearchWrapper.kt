package com.priorDev.pokerroutejc.presentation.pokemonSearch

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.ui.Routes

fun NavGraphBuilder.pkSearchWrapper() {
    composable<Routes.PkSearch> {
        val searchViewModel = hiltViewModel<PkSearchViewModel>()
        val pokemonNames = searchViewModel.pokemonNames

        PkSearchView(
            pokemonNames = pokemonNames,
            onEvent = searchViewModel::onEvent
        )
    }
}
