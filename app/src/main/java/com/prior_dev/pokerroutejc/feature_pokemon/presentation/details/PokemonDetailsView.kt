package com.prior_dev.pokerroutejc.feature_pokemon.presentation.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.EnumColorTypes
import com.prior_dev.pokerroutejc.core.components.CommonStatesView
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonData
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.PokemonInfo
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.MovesView
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.SpritesView
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.components.WeaknessesAndStrengthView
import com.prior_dev.pokerroutejc.feature_types.domain.getColor
import com.prior_dev.pokerroutejc.ui.theme.Purple500
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonDetailsView(
    navPokemon: NavHostController
) {
    val viewModel: PokemonDetailsViewModel = hiltViewModel()
    val states by viewModel.states.observeAsState(CommonStates())
    val pokemon by viewModel.pokemon.observeAsState(PokemonData())
    val weaknessesAndStrengths by viewModel.weaknessesAndStrengths.observeAsState()

    val cardPadding  = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    val scope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()

    val colorTypes = if(pokemon.types.isEmpty()){
        listOf(
            EnumColorTypes.Normal.color,
            EnumColorTypes.Normal.color,
        )
    }else{
        listOf(
            pokemon.types.first().getColor(),
            pokemon.types.last().getColor()
        )
    }

    DisposableEffect(systemUiController, colorTypes.first()) {
        systemUiController.setSystemBarsColor(
            color = colorTypes.first(),
            darkIcons = true
        )

        onDispose {
            systemUiController.setSystemBarsColor(
                color = Purple500,
                darkIcons = true
            )
        }
    }

    CommonStatesView(onDismiss = viewModel::onDismiss, commonStates = states)
    if(states.isLoading)
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
                    MaterialTheme.colors.secondary
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
                0 -> PokemonInfo(Modifier.fillMaxWidth(), pokemon, navPokemon, cardPadding)
                1 -> weaknessesAndStrengths?.let {
                    WeaknessesAndStrengthView(
                        weaknessesAndStrengths = it,
                        modifier = Modifier.padding(cardPadding)
                    )
                }
                2 -> MovesView()
                3 -> SpritesView(Modifier.padding(cardPadding), pokemon)
                else -> PokemonInfo(Modifier.fillMaxWidth(), pokemon, navPokemon, cardPadding)
            }
        }
    }
}