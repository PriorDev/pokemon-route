package com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prior_dev.pokemonrroutejc.core.CommonStates
import com.prior_dev.pokemonrroutejc.core.components.CommonStatesView
import com.prior_dev.pokemonrroutejc.feature_pokemon.domain.PokemonData
import com.prior_dev.pokemonrroutejc.feature_types.domain.getColor
import com.prior_dev.pokemonrroutejc.ui.theme.Purple500
import com.prior_dev.pokemonrroutejc.core.EnumColorTypes
import com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.components.PokemonBasicInfo
import com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.components.Sprites

@Composable
fun PokemonDetailsView(
    navPokemon: NavHostController
) {
    val viewModel: PokemonDetailsViewModel = hiltViewModel()
    val states by viewModel.states.observeAsState(CommonStates())
    val pokemon by viewModel.pokemon.observeAsState(PokemonData())
    val systemUiController = rememberSystemUiController()
    val cardPadding  = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
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
    val scrollState = rememberScrollState()

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

    CommonStatesView(onDismiss = viewModel::onDismiss, states = states)
    if(states.isLoading)
        return

    Column(
        Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(colorTypes))
            .verticalScroll(scrollState)
    ){
        PokemonBasicInfo(Modifier.fillMaxWidth(), pokemon, navPokemon, cardPadding)

        Spacer(modifier = Modifier.height(16.dp))

        Sprites(Modifier.padding(cardPadding), pokemon)

        Spacer(modifier = Modifier.height(16.dp))

    }

}