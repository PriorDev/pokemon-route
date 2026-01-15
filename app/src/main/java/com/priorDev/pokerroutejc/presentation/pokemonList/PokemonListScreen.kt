package com.priorDev.pokerroutejc.presentation.pokemonList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonNameData
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.ScreenTemplate
import com.priorDev.pokerroutejc.presentation.pokemonList.components.ItemPokemonName
import com.priorDev.pokerroutejc.presentation.reusable.DisposableMessage
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate
import com.priorDev.pokerroutejc.presentation.reusable.SearchBarButton
import com.priorDev.pokerroutejc.ui.Routes

@Composable
fun PokemonListScreen(
    states: PokemonListState,
    pokemonList: LazyPagingItems<PokemonNameData>,
    onEvent: (PokemonListEvent) -> Unit,
) {
    val gridState = rememberLazyGridState()

    DisposableMessage(states.uiMessages, onDismiss = { onEvent(PokemonListEvent.OnDismiss) })

    val loadingIndicator = if (states.isRefreshing) LoadingIndicator.Refreshing else LoadingIndicator.None

    // ScreenTemplate can handle refresh if we pass onRefresh and loadingIndicator
    ScreenTemplate(
        loadingIndicator = loadingIndicator,
        errorState = states.errorState,
        onRefresh = {
            onEvent(PokemonListEvent.OnRefresh)
            pokemonList.refresh()
        },
        onEvent = onEvent
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background
                )
        ) {
            SearchBarButton(
                text = stringResource(R.string.search_pokemon_by_name),
                onClick = { onEvent.invoke(PokemonListEvent.OnSearch) }
            )

            if (states.isLoading || pokemonList.loadState.append is LoadState.Loading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {
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
            ) {
                items(pokemonList.itemCount) { index ->
                    val pokemon = pokemonList[index] ?: return@items

                    ItemPokemonName(
                        pokemon = pokemon,
                        modifier = Modifier.clickable {
                            onEvent(
                                PokemonListEvent.Navigate(
                                    Routes.PkDetails(pokemon.name)
                                )
                            )
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PokemonSearchViewPreview() {
    PreviewTemplate {
        val pager = Pager(PagingConfig(pageSize = 10)) {
            FakeMyItemPagingSource()
        }

        val lazyPagingItems = pager.flow.collectAsLazyPagingItems()

        PokemonListScreen(
            states = PokemonListState(isLoading = true),
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
