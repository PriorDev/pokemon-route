package com.priorDev.pokerroutejc.presentation.pokemonDetails.moves

import MoveBottomSheet
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.presentation.core.ScreenTemplate
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsEvents
import com.priorDev.pokerroutejc.ui.theme.PokemonRRouteJCTheme
import com.priorDev.pokerroutejc.utils.ApiLanguages

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PokemonMovesView(
    pkMovesState: PokemonMovesState,
    selectedLanguage: ApiLanguages,
    movesList: Map<String, List<MoveDetailsData>>,
    onEvents: (PokemonDetailsEvents) -> Unit,
) {
    val cardPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    val moveSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isFiltersExpanded by remember { mutableStateOf(false) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    ScreenTemplate(
        errorState = pkMovesState.errorState,
        loadingIndicator = pkMovesState.loading,
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
        },
        onEvent = onEvents
    ) {
        MoveBottomSheet(
            isVisible = isBottomSheetVisible,
            onDismiss = {
                isBottomSheetVisible = false
                onEvents(
                    PokemonDetailsEvents.SelectMove(null)
                )
            },
            move = pkMovesState.selectedMove,
            sheetState = moveSheetState
        )

        Box {
            LazyColumn {
                item {
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(ApiLanguages.entries) { language ->
                            FilterChip(
                                onClick = {
                                    onEvents(PokemonDetailsEvents.SelectLanguage(language))
                                },
                                label = {
                                    Text(language.key)
                                },
                                selected = language == selectedLanguage,
                                leadingIcon = {
                                    if (language == selectedLanguage) {
                                        Icon(
                                            imageVector = Icons.Outlined.Check,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                        }
                    }
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
                filters = pkMovesState.moveCriteria,
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

@Preview
@Composable
private fun PokemonMovesViewPreview() {
    PokemonRRouteJCTheme {
        PokemonMovesView(
            pkMovesState = PokemonMovesState(),
            selectedLanguage = ApiLanguages.ENGLISH,
            movesList = mapOf(
                "level-up" to listOf(
                    MoveDetailsData(
                        visible = true,
                        learnMethod = "level-up",
                        name = "Tackle",
                        accuracy = 100,
                        power = 40,
                        pp = 35,
                        priority = 0,
                        type = null,
                        damageClass = "physical",
                        generationName = "generation-i",
                        effect = "Inflicts regular damage.",
                        machineNumber = "",
                        level = 1
                    )
                ),
                "machine" to listOf(
                    MoveDetailsData(
                        visible = true,
                        learnMethod = "machine",
                        name = "Thunderbolt",
                        accuracy = 100,
                        power = 90,
                        pp = 15,
                        priority = 0,
                        type = null,
                        damageClass = "special",
                        generationName = "generation-i",
                        effect = "Has a 10% chance to paralyze the target.",
                        machineNumber = "TM24",
                        level = 0
                    )
                )
            ),
            onEvents = {}
        )
    }
}