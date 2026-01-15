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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.presentation.core.ScreenTemplate
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.presentation.pokemonDetails.evolution.EvolutionChainView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.evolution.EvolutionState
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.PageIndicator
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.PokemonMovesState
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.PokemonMovesView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.pkInfo.PokemonInfo
import com.priorDev.pokerroutejc.presentation.pokemonDetails.sprites.SpritesState
import com.priorDev.pokerroutejc.presentation.pokemonDetails.sprites.SpritesView
import com.priorDev.pokerroutejc.presentation.pokemonDetails.typeRelation.DamageRelationStates
import com.priorDev.pokerroutejc.presentation.pokemonDetails.typeRelation.DamageRelationView
import com.priorDev.pokerroutejc.presentation.reusable.DisposableMessage
import com.priorDev.pokerroutejc.presentation.utils.PageItem
import com.priorDev.pokerroutejc.presentation.utils.PkDetailsPages
import com.priorDev.pokerroutejc.ui.theme.PokemonRRouteJCTheme
import com.priorDev.pokerroutejc.utils.ApiLanguages

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailsScreen(
    states: PokemonDetailsStates,
    pkMovesState: PokemonMovesState,
    damageRelationStates: DamageRelationStates,
    evolutionState: EvolutionState,
    spritesState: SpritesState,
    movesList: Map<String, List<MoveDetailsData>>,
    onEvents: (PokemonDetailsEvents) -> Unit,
    selectedLanguage: ApiLanguages,
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

    ScreenTemplate(
        loadingIndicator = states.loading,
        errorState = states.errorState,
        onEvent = onEvents,
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
    ) {
        HorizontalPager(
            state = pagerState
        ) { pageIndex ->
            when (pages[pageIndex].page) {
                PkDetailsPages.EVOLUTION_CHAIN -> {
                    EvolutionChainView(
                        states = evolutionState
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
                        states = damageRelationStates,
                        modifier = Modifier.padding(cardPadding),
                        onEvents = onEvents
                    )
                }
                PkDetailsPages.POKEMON_MOVES -> {
                    PokemonMovesView(
                        pkMovesState = pkMovesState,
                        movesList = movesList,
                        onEvents = onEvents,
                        selectedLanguage = selectedLanguage
                    )
                }
                PkDetailsPages.SPRITES -> {
                    SpritesView(states = spritesState)
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PokemonDetailsScreenPreview() {
    PokemonRRouteJCTheme {
        PokemonDetailsScreen(
            states = PokemonDetailsStates(),
            pkMovesState = PokemonMovesState(),
            damageRelationStates = DamageRelationStates(),
            evolutionState = EvolutionState(),
            spritesState = SpritesState(),
            movesList = emptyMap(),
            onEvents = {},
            selectedLanguage = ApiLanguages.ENGLISH
        )
    }
}
