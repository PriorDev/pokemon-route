package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.core.EnumColorTypes
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.domain.types.models.getColor
import com.priorDev.pokerroutejc.presentation.core.AlertDialogModel
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.ScreenTemplate
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.presentation.pokemonDetails.evolution.EvolutionChainView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.MovesView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.pkInfo.PokemonInfo
import com.priorDev.pokerroutejc.presentation.pokemonDetails.sprites.SpritesView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.typeRelation.WeaknessesAndStrengthView
import com.priorDev.pokerroutejc.presentation.reusable.CommonStatesView
import com.priorDev.pokerroutejc.presentation.utils.PageItem
import com.priorDev.pokerroutejc.utils.capitalized
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailsScreen(
    commonStates: CommonStates,
    states: PokemonDetailsStates,
    movesList: Map<String, List<MoveDetailsData>>,
    onEvents: (PokemonDetailsEvents) -> Unit,
) {
    val cardPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    val scope = rememberCoroutineScope()

    val pages = listOf(
        PageItem(
            index = 0,
            title = UiMessages.StringResource(R.string.evolution_chain, states.pokemon.name)
        ) {
            EvolutionChainView(
                states = states
            )
        },
        PageItem(
            index = 1,
            title = UiMessages.DynamicMessage(states.pokemon.name)
        ) {
            PokemonInfo(
                modifier = Modifier.fillMaxWidth(),
                states = states,
                onEvents = onEvents,
                cardPadding = cardPadding
            )
        },
        PageItem(
            index = 2,
            title = UiMessages.StringResource(R.string.weakness_and_strengths, states.pokemon.name)
        ) {
            WeaknessesAndStrengthView(
                states = states,
                modifier = Modifier.padding(cardPadding)
            )
        },
        PageItem(
            index = 3,
            title = UiMessages.StringResource(R.string.moves, states.pokemon.name)
        ) {
            MovesView(
                states = states,
                movesList = movesList,
                onEvents = onEvents,
            )
        },
        PageItem(
            index = 4,
            title = UiMessages.StringResource(R.string.sprites, states.pokemon.name)
        ) {
            SpritesView(states = states)
        }
    )

    val pagerState = rememberPagerState(
        pageCount = { pages.size },
        initialPage = 1
    )

    CommonStatesView(
        onDismiss = { onEvents(PokemonDetailsEvents.OnDismiss) },
        commonStates = commonStates
    )

    ScreenTemplate(
        loadingIndicator = if (commonStates.isLoading) LoadingIndicator.SolidSpinningWheel else LoadingIndicator.None,
        errorState = ErrorState(),
        dialogModel = AlertDialogModel(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = pages[pagerState.currentPage].title.asString(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )

                        PageIndicator(pageCount = pages.size, pagerState = pagerState, scope = scope)
                    }
                }
            )
        },
    ) {
        HorizontalPager(
            state = pagerState,
        ) { pageIndex ->
            pages[pageIndex].content.invoke()
        }
    }
}

@Composable
private fun PageIndicator(
    pageCount: Int,
    pagerState: PagerState,
    scope: CoroutineScope
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(pageCount) { tab ->
            val color = if (pagerState.currentPage == tab) {
                MaterialTheme.colorScheme.inversePrimary
            } else {
                Color.LightGray
            }

            Canvas(
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(tab)
                        }
                    }
                    .size(20.dp)
            ) {
                drawCircle(color = color)
            }
        }
    }
}
