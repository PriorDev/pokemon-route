package com.prior_dev.pokerroutejc.feature_pokemon.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.components.DisposableMessage
import com.prior_dev.pokerroutejc.core.components.PreviewTemplate
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonNameData
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.ItemPokemonName
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.PokemonSearchTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PokemonSearchView(
    commonStates: CommonStates,
    pokemonList: List<PokemonNameData>,
    onEvent: (PokemonSearchEvent) -> Unit,
    onUIEvent: (PokemonSearchUiEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    DisposableMessage(commonStates.uiMessages, onDismiss = { onEvent(PokemonSearchEvent.onDismiss) })

    Column {
        PokemonSearchTextField(
            value = commonStates.searchText,
            onValueChange = {
                onEvent(PokemonSearchEvent.OnSearchText(it))
                coroutineScope.launch {
                    gridState.animateScrollToItem(0)
                }
            },
            onSearch = {
                keyboardController?.hide()
            },
        )

        if(commonStates.isLoading){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }else{
            Spacer(modifier = Modifier.height(ProgressIndicatorDefaults.StrokeWidth))
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
            items(pokemonList){ pokemon ->
                ItemPokemonName(
                    pokemon = pokemon,
                    modifier = Modifier.clickable {
                        onUIEvent(PokemonSearchUiEvent.openPokemonDetailsView(pokemon.name))
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if(!gridState.canScrollForward && pokemonList.isNotEmpty()){
            onEvent(PokemonSearchEvent.getNextPage)
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PokemonSearchViewPreview() {
    PreviewTemplate {
        val pokemons = listOf(
            PokemonNameData(1, "Charmander", ""),
            PokemonNameData(1, "Totodile", ""),
            PokemonNameData(1, "Croconawa", ""),
            PokemonNameData(1, "Mew", ""),
            PokemonNameData(1, "Mewtwo", ""),
            PokemonNameData(1, "Fuecoco", ""),
            PokemonNameData(1, "Sprigatito", ""),
        )

        PokemonSearchView(
            commonStates = CommonStates(isLoading = true, searchText = "Buscando ando"),
            pokemonList = pokemons,
            onEvent = { },
            onUIEvent = { }
        )
    }
}