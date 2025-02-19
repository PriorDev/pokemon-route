package com.priorDev.pokerroutejc.featurePokemon.presentation.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.utils.Routes

fun NavGraphBuilder.pkSearchWrapper() {
    composable<Routes.PkSearch> {
//        val state = PkSearchState(
//            searchText = ""
//        )
        PkSearchView(
//            state = state,
//            onEvent = {}
        )
    }
}
