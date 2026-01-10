package com.priorDev.pokerroutejc.presentation.pokedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.priorDev.pokerroutejc.domain.pokedex.models.PokedexEntriesData
import com.priorDev.pokerroutejc.presentation.core.MyTopBar
import com.priorDev.pokerroutejc.presentation.core.ScreenTemplate
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.ui.theme.PokemonRRouteJCTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokedexScreen(
    states: PokedexStates,
    onEvent: (PokedexEvent) -> Unit
) {
    var selectedEntry by remember {
        mutableStateOf<PokedexEntriesData?>(null)
    }


    ScreenTemplate(
        errorState = states.errorState,
        loadingIndicator = states.loading,
        topBar = {
            MyTopBar(
                title = UiMessages.DynamicMessage(states.pokedexName)
            )
        }
    ) {
        PokedexEntryDialog(
            onDismissRequest = {
                selectedEntry = null
            },
            selectedEntry = selectedEntry
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(96.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(states.entries) { entry ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {
                                onEvent(
                                    PokedexEvent.OnNavigate(
                                        Routes.PkDetails(entry.pokemonId.toString())
                                    )
                                )
                            },
                            onLongClick = {
                                selectedEntry = entry
                            }
                        )
                ) {
                    Text(
                        text = "#${entry.entryNumber}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    AsyncImage(
                        model = entry.pokemonImage,
                        contentDescription = null
                    )

                    Text(
                        text = entry.pokemonName,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PokedexScreenPreview() {
    PokemonRRouteJCTheme {
        PokedexScreen(
            states = PokedexStates(
                pokedexName = "Kanto",
                entries = listOf(
                    PokedexEntriesData(
                        entryNumber = 1,
                        pokemonId = 1,
                        pokemonName = "Bulbasaur",
                        pokemonImage = "",
                        types = emptyList()
                    ),
                    PokedexEntriesData(
                        entryNumber = 2,
                        pokemonId = 2,
                        pokemonName = "Ivysaur",
                        pokemonImage = "",
                        types = emptyList()
                    ),
                    PokedexEntriesData(
                        entryNumber = 3,
                        pokemonId = 3,
                        pokemonName = "Venusaur",
                        pokemonImage = "",
                        types = emptyList()
                    )
                )
            ),
            onEvent = {}
        )
    }
}
