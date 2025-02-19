package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.presentation.core.AlertDialogModel
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.ScreenTemplate
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.presentation.pokemonDetails.evolution.EvolutionChainView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.MovesView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.PageIndicator
import com.priorDev.pokerroutejc.presentation.pokemonDetails.pkInfo.PokemonInfo
import com.priorDev.pokerroutejc.presentation.pokemonDetails.sprites.SpritesView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.typeRelation.WeaknessesAndStrengthView
import com.priorDev.pokerroutejc.presentation.reusable.CommonStatesView
import com.priorDev.pokerroutejc.presentation.utils.PageItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailsScreen(
    commonStates: CommonStates,
    states: PokemonDetailsStates,
    movesList: Map<String, List<MoveDetailsData>>,
    onEvents: (PokemonDetailsEvents) -> Unit,
) {
    val cardPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)

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

                        PageIndicator(
                            pageCount = pages.size,
                            pagerState = pagerState,
                        )
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


