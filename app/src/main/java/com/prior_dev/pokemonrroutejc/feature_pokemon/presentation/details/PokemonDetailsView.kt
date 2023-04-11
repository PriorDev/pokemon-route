package com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.details

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prior_dev.pokemonrroutejc.core.CommonStates
import com.prior_dev.pokemonrroutejc.core.components.CommonStatesView
import com.prior_dev.pokemonrroutejc.feature_pokemon.domain.PokemonData
import com.prior_dev.pokemonrroutejc.feature_types.domain.getColor
import com.prior_dev.pokemonrroutejc.ui.theme.Purple500
import com.prior_dev.pokemonrroutejc.R
import com.prior_dev.pokemonrroutejc.core.EnumColorTypes
import com.prior_dev.pokemonrroutejc.core.components.ItemType
import com.prior_dev.pokemonrroutejc.core.components.MySimpleSlider
import com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.RoutesPokemon

@Composable
fun PokemonDetailsView(
    navPokemon: NavHostController
) {
    val viewModel: PokemonDetailsViewModel = hiltViewModel()
    val states by viewModel.states.observeAsState(CommonStates())
    val pokemon by viewModel.pokemon.observeAsState(PokemonData())
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

    CommonStatesView(onDismiss = viewModel::onDismiss, states = states)
    if(states.isLoading)
        return

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(colorTypes)
            )
    ) {
        item{
            Text(
                text = pokemon.name.uppercase(),
                style = MaterialTheme.typography.h4,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                maxLines = 1,
                textAlign = TextAlign.Center,
                color = Color.Black,
            )
        }

        item{
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                pokemon.types.forEach{
                    ItemType(
                        type = it,
                        modifier = Modifier
                            .width(100.dp)
                    ){
                        navPokemon.navigate(RoutesPokemon.TypeDetails.getRoute(it.name))
                    }
                }
            }
        }
        
        item {
            Box(modifier = Modifier.fillMaxWidth()){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(pokemon.sprites.frontDefault)
                        .crossfade(true)
                        .build(),
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center)
                )
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(pokemon.sprites.frontShiny)
                        .crossfade(true)
                        .build(),
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(150.dp)
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(pokemon.sprites.backShiny)
                        .crossfade(true)
                        .build(),
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(150.dp)
                )
            }
        }

        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)){
                Card{
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background)
                    ) {
                        Text(
                            text = stringResource(id = R.string.abilities),
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                        pokemon.abilities.filter { !it.isHidden }.forEach{ ability ->
                            Text(
                                text = ability.name,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .background(MaterialTheme.colors.background),
                                textAlign = TextAlign.Center,
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }

        item{
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)){
                Card{
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background)
                    ) {
                        Text(
                            text = stringResource(id = R.string.hidden_ability),
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                        pokemon.abilities.filter { it.isHidden }.forEach{ ability ->
                            Text(
                                text = ability.name,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                                    .background(MaterialTheme.colors.background),
                                textAlign = TextAlign.Center,
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }

        item{
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)){
                Card{
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background)
                    ) {
                        Text(
                            text = stringResource(id = R.string.stats),
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                        pokemon.stats.forEach{ stat ->
                            MySimpleSlider(
                                label = stat.name,
                                value = stat.baseStat.toFloat(),
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}