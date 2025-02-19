package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.presentation.pokemonDetails.evolution.EvolutionChainView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.PokemonMovesView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.PageIndicator
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.PokemonMovesState
import com.priorDev.pokerroutejc.presentation.pokemonDetails.pkInfo.PokemonInfo
import com.priorDev.pokerroutejc.presentation.pokemonDetails.sprites.SpritesView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.typeRelation.DamageRelationView
import com.priorDev.pokerroutejc.presentation.utils.PageItem
import com.priorDev.pokerroutejc.presentation.utils.PkDetailsPages

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailsScreen(
    pkMovesState: PokemonMovesState,
    states: PokemonDetailsStates,
    movesList: Map<String, List<MoveDetailsData>>,
    onEvents: (PokemonDetailsEvents) -> Unit,
) {
    val cardPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)

    val pages = listOf(
        PageItem(
            index = 0,
            title = UiMessages.StringResource(R.string.evolution_chain, states.pokemon.name),
            page = PkDetailsPages.EVOLUTION_CHAIN
        ),
        PageItem(
            index = 1,
            title = UiMessages.DynamicMessage(states.pokemon.name),
            page = PkDetailsPages.POKEMON_INFO
        ),
        PageItem(
            index = 2,
            title = UiMessages.StringResource(R.string.damage_relation, states.pokemon.name),
            page = PkDetailsPages.DAMAGE_RELATION
        ),
        PageItem(
            index = 3,
            title = UiMessages.StringResource(R.string.moves, states.pokemon.name),
            page = PkDetailsPages.POKEMON_MOVES
        ),
        PageItem(
            index = 4,
            title = UiMessages.StringResource(R.string.sprites, states.pokemon.name),
            page = PkDetailsPages.SPRITES
        )
    )

    val pagerState = rememberPagerState(
        pageCount = { pages.size },
        initialPage = 1
    )

    Scaffold(
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
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(innerPadding)
        ) { pageIndex ->
            when (pages[pageIndex].page) {
                PkDetailsPages.EVOLUTION_CHAIN -> {
                    EvolutionChainView(
                        states = states
                    )
                }
                PkDetailsPages.POKEMON_INFO -> {
                    PokemonInfo(
                        modifier = Modifier.fillMaxWidth(),
                        states = states,
                        onEvents = onEvents,
                        cardPadding = cardPadding
                    )
                }
                PkDetailsPages.DAMAGE_RELATION -> {
                    DamageRelationView(
                        states = states,
                        modifier = Modifier.padding(cardPadding)
                    )
                }
                PkDetailsPages.POKEMON_MOVES -> {
                    PokemonMovesView(
                        pkMovesState = pkMovesState,
                        movesList = movesList,
                        onEvents = onEvents,
                    )
                }
                PkDetailsPages.SPRITES -> {
                    SpritesView(states = states)
                }
            }
        }
    }
}


