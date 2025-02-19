package com.priorDev.pokerroutejc.featurePokemon.presentation.pokemonList

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.priorDev.pokerroutejc.utils.Routes

fun NavGraphBuilder.pokemonListWrapper() {
    composable<Routes.PokemonList> {
        val viewModel: PokemonListViewModel = hiltViewModel()
        val commonStates by viewModel.commonStates.collectAsStateWithLifecycle()
        val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
        val pokemonList = viewModel.pokemonList.collectAsLazyPagingItems()

        PokemonListView(
            commonStates = commonStates,
            pokemonList = pokemonList,
            onEvent = viewModel::onEvent,
            isRefreshing = isRefreshing
        )
    }
}
