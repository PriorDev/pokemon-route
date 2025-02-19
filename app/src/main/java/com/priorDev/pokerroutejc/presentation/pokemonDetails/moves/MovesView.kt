package com.priorDev.pokerroutejc.presentation.pokemonDetails.moves

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
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
    var expandedFilters by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    expandedFilters = !expandedFilters
                },
            ) {
                val contentDescription = stringResource(R.string.filter)
                Icon(
                    imageVector = if (expandedFilters) Icons.Outlined.Check else Icons.Outlined.FilterAlt,
                    contentDescription = contentDescription
                )
            }
        }
    ) { innerPadding ->
        Box {
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
                            StickyHeader(
                                learnMethod = learnMethod,
                                moves = moves,
                                onEvents = onEvents
                            )
                        }

                        items(moves.filter { it.visible }) { move ->
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

            FilterBox(
                filters = states.moveFilters,
                onDismiss = {
                    expandedFilters = false
                },
                expandedFilters = expandedFilters,
                onItemClick = {
                    onEvents(PokemonDetailsEvents.ToggleMoveFilterCheck(it))
                }
            )
        }
    }
}

@Composable
private fun StickyHeader(
    learnMethod: String,
    moves: List<MoveDetailsData>,
    onEvents: (PokemonDetailsEvents) -> Unit
) {
    val isExpanded by remember(moves) {
        derivedStateOf { moves.any { it.visible } }
    }
    Card(
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = learnMethod,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )

            IconToggleButton(
                checked = isExpanded,
                onCheckedChange = {
                    onEvents(
                        PokemonDetailsEvents.ToggleLearnMethodExpand(
                            learnMethod = learnMethod,
                            isExpanded = it
                        )
                    )
                }
            ) {
                if (isExpanded) {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.filter)
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.filter)
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterBox(
    filters: List<MoveFilterModel>,
    onDismiss: () -> Unit,
    expandedFilters: Boolean,
    onItemClick: (MoveFilterModel) -> Unit
) {
    if (expandedFilters) {
        Box(
            Modifier
                .fillMaxSize()
                .clickable { onDismiss.invoke() }
                .background(
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
                )
        ) {
            Card(
                modifier = Modifier
                    .padding(bottom = 90.dp)
                    .align(Alignment.TopEnd)
                    .clickable {  },
                elevation = CardDefaults.elevatedCardElevation(disabledElevation = 6.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .background(color = FloatingActionButtonDefaults.containerColor)
                ) {
                    items(filters) { filter ->
                        Row(
                            modifier = Modifier
                                .padding(end = 90.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = filter.checked,
                                onCheckedChange = {
                                    onItemClick(filter)
                                }
                            )
                            Text(text = filter.type.name.uppercase())
                        }
                    }
                }
            }
        }
    }
}