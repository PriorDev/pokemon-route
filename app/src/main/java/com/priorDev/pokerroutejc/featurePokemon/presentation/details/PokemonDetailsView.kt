package com.priorDev.pokerroutejc.featurePokemon.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.core.EnumColorTypes
import com.priorDev.pokerroutejc.presentation.reusable.CommonStatesView
import com.priorDev.pokerroutejc.featurePokemon.domain.MoveDetailsData
import com.priorDev.pokerroutejc.featurePokemon.presentation.components.EvolutionChainView
import com.priorDev.pokerroutejc.featurePokemon.presentation.components.MovesView
import com.priorDev.pokerroutejc.featurePokemon.presentation.components.PokemonInfo
import com.priorDev.pokerroutejc.featurePokemon.presentation.components.SpritesView
import com.priorDev.pokerroutejc.featurePokemon.presentation.components.WeaknessesAndStrengthView
import com.priorDev.pokerroutejc.featureTypes.domain.getColor
import kotlinx.coroutines.launch

@Composable
fun PokemonDetailsView(
    commonStates: CommonStates,
    states: PokemonDetailsStates,
    movesList: List<MoveDetailsData>,
    onEvents: (PokemonDetailsEvents) -> Unit,
) {
    val cardPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    val scope = rememberCoroutineScope()

    val colorTypes = if (states.pokemon.types.isEmpty()) {
        listOf(
            EnumColorTypes.Normal.color,
            EnumColorTypes.Normal.color,
        )
    } else {
        listOf(
            states.pokemon.types.first().getColor(),
            states.pokemon.types.last().getColor()
        )
    }

    CommonStatesView(
        onDismiss = { onEvents(PokemonDetailsEvents.OnDismiss) },
        commonStates = commonStates
    )

    if (commonStates.isLoading) return

    val pageCount = 5
    val pagerState = rememberPagerState(
        pageCount = { pageCount },
        initialPage = 1
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(colorTypes))
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(pageCount) { tab ->
                val color = if (pagerState.currentPage == tab) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    Color.LightGray
                }

                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .height(4.dp)
                        .width(80.dp)
                        .background(color)
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(tab)
                            }
                        }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .offset(y = 30.dp)
        ) { page ->
            when (page) {
                0 -> {
                    EvolutionChainView(
                        states = states
                    )
                }

                1 -> {
                    PokemonInfo(
                        modifier = Modifier.fillMaxWidth(),
                        states = states,
                        onEvents = onEvents,
                        cardPadding = cardPadding
                    )
                }

                2 -> {
                    WeaknessesAndStrengthView(
                        states = states,
                        modifier = Modifier.padding(cardPadding)
                    )
                }

                3 -> {
                    MovesView(
                        states = states,
                        movesList = movesList,
                        onEvents = onEvents
                    )
                }

                4 -> {
                    SpritesView(
                        modifier = Modifier.padding(cardPadding),
                        states = states
                    )
                }

                else -> {
                    PokemonInfo(
                        modifier = Modifier.fillMaxWidth(),
                        states = states,
                        cardPadding = cardPadding,
                        onEvents = onEvents,
                    )
                }
            }
        }
    }
}
