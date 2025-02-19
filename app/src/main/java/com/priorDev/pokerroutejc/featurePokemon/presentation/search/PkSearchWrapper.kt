package com.priorDev.pokerroutejc.featurePokemon.presentation.search

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.utils.Routes

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
