package com.priorDev.pokerroutejc.presentation.pokemonDetails.sprites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsStates

@Composable
fun SpritesView(
    states: PokemonDetailsStates,
    modifier: Modifier = Modifier
) {
    val pokemon = states.pokemon

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        pokemon.sprites.others?.officialArtFrontShiny.let { officialArtFrontShiny ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(officialArtFrontShiny)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth()
            )
        }

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
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(.5f)
                    .height(200.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.sprites.backShiny)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(.5f)
                    .height(200.dp)
            )
        }
    }
}
