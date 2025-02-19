package com.priorDev.pokerroutejc.presentation.pokemonDetails.moves

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsEvents
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsStates

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovesView(
    states: PokemonDetailsStates,
    movesList: List<MoveDetailsData>,
    onEvents: (PokemonDetailsEvents) -> Unit
) {
    val cardPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)

    Column(Modifier.fillMaxSize()) {
        if (states.isLoading) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
        }
        LazyColumn {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.moves),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth(.6f)
                    )

                    androidx.compose.animation.AnimatedVisibility(
                        visible = !states.isLoading,
                        enter = slideInHorizontally(
                            initialOffsetX = { it }
                        ),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(0.dp)
                                .background(MaterialTheme.colorScheme.primary),
                            onClick = { onEvents(PokemonDetailsEvents.OnToggleFilterVisibility) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.filters),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
            item {
                AnimatedVisibility(visible = states.isFiltersExpanded) {
                    Filters(states = states, moveList = movesList, onEvents = onEvents)
                }
            }
            items(movesList) { move ->
                if (move.isVisible) {
                    ItemMove(
                        move = move,
                        modifier = Modifier
                            .padding(cardPadding)
                            .animateItemPlacement()
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}
