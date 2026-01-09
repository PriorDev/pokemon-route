package com.priorDev.pokerroutejc.presentation.pokemonList

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.priorDev.pokerroutejc.ui.Routes
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.pokemonListWrapper() {
    composable<Routes.PokemonList> {
        val viewModel: PokemonListViewModel = koinViewModel()
        val states by viewModel.states.collectAsStateWithLifecycle()
        val pokemonList = viewModel.pokemonList.collectAsLazyPagingItems()

        PokemonListScreen(
            states = states,
            pokemonList = pokemonList,
            onEvent = viewModel::onEvent
        )
    }
}
