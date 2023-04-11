package com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prior_dev.pokemonrroutejc.R
import com.prior_dev.pokemonrroutejc.core.CommonStates
import com.prior_dev.pokemonrroutejc.core.components.DisposableMessage
import com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.RoutesPokemon
import com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.components.ItemPokemon
import com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.components.PokemonSearchTextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokemonSearchView(
    navPokemon: NavHostController
) {
    val viewModel: PokemonSearchViewModel = hiltViewModel()
    val states by viewModel.states.observeAsState(CommonStates())
    val keyboardController = LocalSoftwareKeyboardController.current

    DisposableMessage(states.message, onDismiss = viewModel::onDismiss)

    Column {
        PokemonSearchTextField(
            value = states.searchText,
            onValueChange = viewModel::onSearchText,
            onSearch = {
                viewModel.onSearchClick()
                keyboardController?.hide()
            },
        )

        Box(
            Modifier.fillMaxSize()
        ){
            if(!states.isLoading && viewModel.pokemonNames.isEmpty()){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(top = 4.dp)
                ){
                    items(viewModel.pokemons){pokemon ->
                        ItemPokemon(
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
            }

            LazyColumn{
                if(states.isLoading){
                    item{
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                }else{
                    items(viewModel.pokemonNames){ pokemonsName  ->
                        Text(
                            text = pokemonsName.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                                .clickable {
                                    navPokemon.navigate(
                                        RoutesPokemon.PokemonDetails.getRoute(pokemonsName.name)
                                    )
                                }
                        )
                        Divider()
                    }
                }
            }
        }
    }

}
