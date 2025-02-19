package com.prior_dev.pokerroutejc.feature_pokemon.presentation.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.EnumColorTypes
import com.prior_dev.pokerroutejc.core.components.CommonStatesView
import com.prior_dev.pokerroutejc.feature_pokemon.domain.MoveDetailsData
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.MovesView
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.PokemonInfo
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.SpritesView
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.WeaknessesAndStrengthView
import com.prior_dev.pokerroutejc.feature_types.domain.getColor
import com.prior_dev.pokerroutejc.ui.theme.md_theme_light_primary
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonDetailsView(
    commonStates: CommonStates,
    states: PokemonDetailsStates,
    movesList: List<MoveDetailsData>,
    onEvents: (PokemonDetailsEvents) -> Unit,
) {
    val cardPadding  = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    val scope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()

    val colorTypes = if(states.pokemon.types.isEmpty()){
        listOf(
            EnumColorTypes.Normal.color,
            EnumColorTypes.Normal.color,
        )
    }else{
        listOf(
            states.pokemon.types.first().getColor(),
            states.pokemon.types.last().getColor()
        )
    }

    DisposableEffect(systemUiController, colorTypes.first()) {
        systemUiController.setSystemBarsColor(
            color = colorTypes.first(),
            darkIcons = true
        )

        onDispose {
            systemUiController.setSystemBarsColor(
                color = md_theme_light_primary,
                darkIcons = true
            )
        }
    }

    CommonStatesView(
        onDismiss = { onEvents(PokemonDetailsEvents.OnDismiss) },
        commonStates = commonStates
    )

    if(commonStates.isLoading)
        return

    val pageCount = 4
    val pagerState = rememberPagerState()

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(colorTypes))
    ){
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(pageCount){ tab ->
                val color = if (pagerState.currentPage == tab)
                    MaterialTheme.colorScheme.secondary
                else
                    Color.LightGray

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
            pageCount = pageCount,
            state = pagerState,
            modifier = Modifier
                .offset(y = 30.dp)
        ) { page ->
            when(page){
                0 -> PokemonInfo(
                        modifier = Modifier.fillMaxWidth(),
                        states = states,
                        onEvents = onEvents,
                        cardPadding = cardPadding
                    )
                1 -> WeaknessesAndStrengthView(
                        states = states,
                        modifier = Modifier.padding(cardPadding)
                    )
                2 -> MovesView(
                    states = states,
                    movesList = movesList,
                    onEvents = onEvents
                )
                3 -> SpritesView(
                        modifier = Modifier.padding(cardPadding),
                        states = states
                    )
                else -> PokemonInfo(
                    modifier = Modifier.fillMaxWidth(),
                    states = states,
                    cardPadding = cardPadding,
                    onEvents = onEvents,
                )
            }
        }
    }
}