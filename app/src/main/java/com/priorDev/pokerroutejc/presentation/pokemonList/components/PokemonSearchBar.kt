package com.priorDev.pokerroutejc.presentation.pokemonList.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonNameData
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonSearchBar(
    query: String,
    isActive: Boolean,
    onQueryChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    searchResults: List<PokemonNameData>,
    onResultClick: (PokemonNameData) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = if (isActive) 0.dp else 4.dp)
    ) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = { keyboardController?.hide() },
                    expanded = isActive,
                    onExpandedChange = onActiveChange,
                    placeholder = { Text("Search Pokemon") },
                    leadingIcon = {
                        if (isActive) {
                            IconButton(onClick = {
                                onActiveChange(false)
                                onClear()
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
                        if (isActive && query.isNotEmpty()) {
                            IconButton(onClick = {
                                onClear()
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
            expanded = isActive,
            onExpandedChange = onActiveChange,
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                items(searchResults) { pokemon ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onResultClick(pokemon) }
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

    BackHandler(enabled = isActive) {
        onActiveChange(false)
        onClear()
    }
}

@PreviewLightDark
@Composable
private fun PokemonSearchBarPreviewUnexpanded() {
    PreviewTemplate {
        PokemonSearchBar(
            query = "",
            isActive = false,
            onQueryChange = {},
            onActiveChange = {},
            searchResults = emptyList(),
            onResultClick = {},
            onClear = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PokemonSearchBarPreviewExpanded() {
    PreviewTemplate {
        PokemonSearchBar(
            query = "Char",
            isActive = true,
            onQueryChange = {},
            onActiveChange = {},
            searchResults = emptyList(),
            onResultClick = {},
            onClear = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PokemonSearchBarPreviewWithResults() {
    PreviewTemplate {
        PokemonSearchBar(
            query = "Char",
            isActive = true,
            onQueryChange = {},
            onActiveChange = {},
            searchResults = listOf(
                PokemonNameData(1, "Charmander", ""),
                PokemonNameData(2, "Charmeleon", ""),
                PokemonNameData(3, "Charizard", "")
            ),
            onResultClick = {},
            onClear = {}
        )
    }
}
