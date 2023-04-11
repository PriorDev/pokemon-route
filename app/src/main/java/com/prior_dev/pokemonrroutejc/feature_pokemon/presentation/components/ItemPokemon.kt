package com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.prior_dev.pokemonrroutejc.core.components.ItemType
import com.prior_dev.pokemonrroutejc.feature_pokemon.domain.PokemonData
import com.prior_dev.pokemonrroutejc.feature_types.domain.getColor

@Composable
fun ItemPokemon(
    modifier: Modifier = Modifier,
    pokemon: PokemonData,
) {
    val colorTypes = listOf(
        pokemon.types.first().getColor(),
        pokemon.types.last().getColor()
    )

    Card (
        modifier = modifier,
        elevation = 8.dp,
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(colorTypes)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = pokemon.name.uppercase(),
                style = MaterialTheme.typography.h5,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(4.dp),
                maxLines = 1,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.sprites.frontDefault)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                Modifier
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                pokemon.types.forEach {
                    ItemType(type = it)
                }
            }
        }
    }
}