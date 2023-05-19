package com.prior_dev.pokerroutejc.feature_pokemon.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonData
import com.prior_dev.pokerroutejc.R

@Composable
fun SpritesView(
    modifier: Modifier = Modifier,
    pokemon: PokemonData
) {
    Card (modifier = modifier){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.sprites),
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Black,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                textAlign = TextAlign.Center,
            )

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
}