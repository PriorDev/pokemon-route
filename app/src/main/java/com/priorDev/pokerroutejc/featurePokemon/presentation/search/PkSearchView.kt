package com.priorDev.pokerroutejc.featurePokemon.presentation.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonNameData
import com.priorDev.pokerroutejc.ui.theme.PokemonRRouteJCTheme
import com.priorDev.pokerroutejc.utils.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PkSearchView(
    pokemonNames: List<PokemonNameData>,
    onEvent: (PkSearchEvent) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val isExpanded by remember(pokemonNames.isEmpty()) {
        mutableStateOf(pokemonNames.isNotEmpty())
    }

    var searchText by remember { mutableStateOf("") }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .focusable(false)
            .then(
                if (pokemonNames.isEmpty()) {
                    Modifier
                        .padding(horizontal = 16.dp)
                } else {
                    Modifier
                }
            ),
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .focusable(),
                query = searchText,
                onQueryChange = {
                    searchText = it
                    onEvent(PkSearchEvent.OnSearch(searchText))
                },
                onSearch = {
                    keyboardController?.hide()
                },
                expanded = false,
                onExpandedChange = { },
                placeholder = { Text("Pokemon Name") },
                leadingIcon = {
                    if (isExpanded) {
                        IconButton(
                            onClick = {
                                onEvent(PkSearchEvent.OnNavigateUp)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Return to Pokemon List"
                            )
                        }
                    }
                }
            )
        },
        expanded = isExpanded,
        onExpandedChange = { }
    ) {
        BackHandler {
            onEvent(PkSearchEvent.OnNavigateUp)
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            items(pokemonNames) { pokemon ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(
                                PkSearchEvent.OnNavigate(Routes.PkDetails(pokemon.name))
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

@Composable
@Preview
private fun PkSearchViewPreview() {
    PokemonRRouteJCTheme {
        PkSearchView(
            pokemonNames = emptyList(),
            onEvent = {}
        )
    }
}
