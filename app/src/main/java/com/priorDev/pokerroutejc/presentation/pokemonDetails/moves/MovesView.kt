package com.priorDev.pokerroutejc.presentation.pokemonDetails.moves

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsEvents
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsStates

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovesView(
    states: PokemonDetailsStates,
    movesList: Map<String, List<MoveDetailsData>>,
    onEvents: (PokemonDetailsEvents) -> Unit,
) {
    val cardPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    val expandedFab by remember { derivedStateOf { true } }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
            ) {
                val contentDescription = stringResource(R.string.filter)
                Icon(Icons.Default.Search, contentDescription = contentDescription)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding
        ) {
            item {
                //Filters(states = states, moveList = movesList, onEvents = onEvents)
            }

            movesList
                .entries
                .reversed()
                .forEach { (learnMethod, moves) ->
                    stickyHeader {
                        Text(
                            text = learnMethod,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .padding(4.dp)
                        )
                    }

                    items(moves) { move ->
                        ItemMove(
                            move = move,
                            modifier = Modifier
                                .padding(cardPadding)
                        )
                    }
                }

            item {
                Spacer(Modifier.height(64.dp))
            }
        }
    }
}
