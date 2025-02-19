package com.prior_dev.pokerroutejc.feature_pokemon.presentation.search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prior_dev.pokerroutejc.core.routes.RoutesPokemon

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.pkSearchWrapper() {
    composable(
        route = RoutesPokemon.PkSearch.route
    ) {
        val state = PkSearchState(
            searchText = ""
        )
        PkSearchView(
            state = state,
            onEvent = {}
        )
    }
}