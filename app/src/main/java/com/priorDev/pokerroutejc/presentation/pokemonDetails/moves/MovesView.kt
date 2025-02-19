package com.priorDev.pokerroutejc.presentation.pokemonDetails.moves

import MoveBottomSheet
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsEvents
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsStates

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MovesView(
    states: PokemonDetailsStates,
    movesList: Map<String, List<MoveDetailsData>>,
    onEvents: (PokemonDetailsEvents) -> Unit,
) {
    val cardPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    val moveSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isFiltersExpanded by remember { mutableStateOf(false) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isFiltersExpanded = !isFiltersExpanded
                },
            ) {
                val contentDescription = stringResource(R.string.filter)
                Icon(
                    imageVector = if (isFiltersExpanded) Icons.Outlined.Check else Icons.Outlined.FilterAlt,
                    contentDescription = contentDescription
                )
            }
        }
    ) { innerPadding ->

        MoveBottomSheet(
            isVisible = isBottomSheetVisible,
            onDismiss = {
                isBottomSheetVisible = false
                onEvents(
                    PokemonDetailsEvents.SelectMove(null)
                )
            },
            move = states.selectedMove,
            sheetState = moveSheetState
        )

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
                                    .padding(cardPadding),
                                onClick = {
                                    isBottomSheetVisible = true
                                    onEvents(
                                        PokemonDetailsEvents.SelectMove(move)
                                    )
                                }
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
                    isFiltersExpanded = false
                },
                expandedFilters = isFiltersExpanded,
                onItemClick = {
                    onEvents(PokemonDetailsEvents.ToggleMoveFilterCheck(it))
                }
            )
        }
    }
}
