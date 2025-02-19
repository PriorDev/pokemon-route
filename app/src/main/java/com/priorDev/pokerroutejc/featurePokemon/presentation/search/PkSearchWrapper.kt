package com.priorDev.pokerroutejc.featurePokemon.presentation.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.core.routes.RoutesPokemon

fun NavGraphBuilder.pkSearchWrapper() {
    composable(
        route = RoutesPokemon.PkSearch.route
    ) {
//        val state = PkSearchState(
//            searchText = ""
//        )
        PkSearchView(
//            state = state,
//            onEvent = {}
        )
    }
}
