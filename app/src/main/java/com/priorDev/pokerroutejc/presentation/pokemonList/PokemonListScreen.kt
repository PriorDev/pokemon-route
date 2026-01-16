package com.priorDev.pokerroutejc.presentation.pokemonList

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonNameData
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.ScreenTemplate
import com.priorDev.pokerroutejc.presentation.pokemonList.components.ItemPokemonName
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate
import com.priorDev.pokerroutejc.ui.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    states: PokemonListState,
    pokemonList: LazyPagingItems<PokemonNameData>,
    onEvent: (PokemonListEvent) -> Unit,
) {
    val gridState = rememberLazyGridState()
    val loadingIndicator = if (states.isRefreshing) LoadingIndicator.Refreshing else LoadingIndicator.None
    val keyboardController = LocalSoftwareKeyboardController.current

    var isSearchActive by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isSearchActive) {
        if (!isSearchActive) {
            onEvent(PokemonListEvent.OnSearchQueryChange(""))
        }
    }

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = if (isSearchActive) 0.dp else 4.dp)
            ) {
                SearchBar(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = states.searchText,
                            onQueryChange = { onEvent(PokemonListEvent.OnSearchQueryChange(it)) },
                            onSearch = { keyboardController?.hide() },
                            expanded = isSearchActive,
                            onExpandedChange = { isSearchActive = it },
                            placeholder = { Text("Search Pokemon") },
                            leadingIcon = {
                                if (isSearchActive) {
                                    IconButton(onClick = {
                                        isSearchActive = false
                                        onEvent(PokemonListEvent.OnSearchQueryChange(""))
                                    }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search"
                                    )
                                }
                            },
                            trailingIcon = {
                                if (isSearchActive && states.searchText.isNotEmpty()) {
                                    IconButton(onClick = {
                                        onEvent(PokemonListEvent.OnSearchQueryChange(""))
                                        isSearchActive = false
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Clear search"
                                        )
                                    }
                                }
                            }
                        )
                    },
                    expanded = isSearchActive,
                    onExpandedChange = { isSearchActive = it },
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 16.dp),
                    ) {
                        items(states.searchResults) { pokemon ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onEvent(
                                            PokemonListEvent.Navigate(
                                                Routes.PkDetails(pokemon.name)
                                            )
                                        )
                                    }
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(pokemon.imgUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = pokemon.name,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(50.dp)
                                )
                                Text(
                                    text = pokemon.name.uppercase(),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(4.dp),
                                )
                            }
                        }
                    }
                }
            }

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
    BackHandler(enabled = isSearchActive) {
        isSearchActive = false
        onEvent(PokemonListEvent.OnSearchQueryChange(""))
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
