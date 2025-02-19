package com.prior_dev.pokerroutejc.feature_pokemon.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.components.DisposableMessage
import com.prior_dev.pokerroutejc.core.components.PreviewTemplate
import com.prior_dev.pokerroutejc.core.routes.RoutesPokemon
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonNameData
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.ItemPokemonName
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.SearchTextField
import com.prior_dev.pokerroutejc.utils.GlobalEventChannel
import kotlinx.coroutines.launch

@Composable
fun PokemonSearchView(
    commonStates: CommonStates,
    pokemonList: LazyPagingItems<PokemonNameData>,
    onEvent: (PokemonSearchEvent) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    DisposableMessage(commonStates.uiMessages, onDismiss = { onEvent(PokemonSearchEvent.OnDismiss) })

    Column {
        SearchTextField(
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
            Spacer(modifier = Modifier.height(4.dp))
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
            items(pokemonList.itemCount){ index ->
                val pokemon = pokemonList[index] ?: return@items

                ItemPokemonName(
                    pokemon = pokemon,
                    modifier = Modifier.clickable {
                        GlobalEventChannel.sendNavigateEvent(
                            RoutesPokemon.PokemonDetails.getRoute(pokemon.name)
                        )
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                if (pokemonList.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PokemonSearchViewPreview() {
    PreviewTemplate {
        val pager = Pager(PagingConfig(pageSize = 10)) {
            FakeMyItemPagingSource()
        }

        val lazyPagingItems = pager.flow.collectAsLazyPagingItems()

        PokemonSearchView(
            commonStates = CommonStates(isLoading = true, searchText = "Buscando ando"),
            pokemonList = lazyPagingItems,
            onEvent = { }
        )
    }
}

private class FakeMyItemPagingSource : PagingSource<Int, PokemonNameData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonNameData> {
        val pokemons = listOf(
            PokemonNameData(1, "Charmander", ""),
            PokemonNameData(1, "Totodile", ""),
            PokemonNameData(1, "Croconawa", ""),
            PokemonNameData(1, "Mew", ""),
            PokemonNameData(1, "Mewtwo", ""),
            PokemonNameData(1, "Fuecoco", ""),
            PokemonNameData(1, "Sprigatito", ""),
        )
        return LoadResult.Page(
            data = pokemons,
            prevKey = null,
            nextKey = null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonNameData>): Int? {
        return null
    }
}