package com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prior_dev.pokemonrroutejc.core.CalculateColors
import com.prior_dev.pokemonrroutejc.core.components.ShadowText
import com.prior_dev.pokemonrroutejc.feature_pokemon.domain.PokemonNameData
import com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.search.PokemonSearchViewModel

@Composable
fun ItemPokemonName(
    modifier: Modifier = Modifier,
    pokemon: PokemonNameData,
) {
    val defaultDominatColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominatColor)
    }

    Card (
        modifier = modifier,
        elevation = 8.dp,
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            dominantColor,
                            defaultDominatColor
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imgUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(150.dp),
                onSuccess = {
                    CalculateColors().calcDominantColor(it.result.drawable){ color ->
                        dominantColor = color
                    }
                }

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pokemon.name.uppercase(),
                style = MaterialTheme.typography.h5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(4.dp),
                maxLines = 1,
            )
        }
    }
}