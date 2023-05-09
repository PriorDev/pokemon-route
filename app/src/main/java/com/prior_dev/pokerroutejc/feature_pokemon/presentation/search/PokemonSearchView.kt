package com.prior_dev.pokerroutejc.feature_pokemon.presentation.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.components.DisposableMessage
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.RoutesPokemon
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.ItemPokemonName
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.PokemonSearchTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokemonSearchView(
    navPokemon: NavHostController
) {
    val viewModel: PokemonSearchViewModel = hiltViewModel()
    val states by viewModel.states.observeAsState(CommonStates())
    val keyboardController = LocalSoftwareKeyboardController.current
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    DisposableMessage(states.message, onDismiss = viewModel::onDismiss)

    Column {
        PokemonSearchTextField(
            value = states.searchText,
            onValueChange = {
                viewModel.onSearchText(it)
                coroutineScope.launch {
                    gridState.animateScrollToItem(0)
                }
            },
            onSearch = {
                keyboardController?.hide()
            },
        )

        if(states.isLoading){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = gridState,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 4.dp)
        ){
            items(viewModel.pokemonNames){pokemon ->
                ItemPokemonName(
                    pokemon = pokemon,
                    modifier = Modifier.clickable {
                        navPokemon
                            .navigate(RoutesPokemon.PokemonDetails.getRoute(pokemon.name))
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if(!gridState.canScrollForward){
            Log.d("TAG", "PokemonSearchView: ${gridState.canScrollForward}")
            viewModel.getNextPage()
        }
    }
}
