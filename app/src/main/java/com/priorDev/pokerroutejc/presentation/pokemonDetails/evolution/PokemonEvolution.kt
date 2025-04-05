package com.priorDev.pokerroutejc.presentation.pokemonDetails.evolution

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.data.network.utils.EndPoints
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionTriggerResponse
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate

@Composable
fun PokemonEvolution(
    name: String,
    imageUrl: String,
    triggerList: List<EvolutionTriggerResponse>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name.uppercase(),
            fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
            fontWeight = FontWeight.Bold
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
        )

        if (triggerList.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    triggerList.forEach { trigger ->
                        trigger.item?.let { item ->
                            Text(
                                text = item.uppercase(),
                                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                fontWeight = FontWeight.Bold
                            )

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(EndPoints.ITEM_IMAGE_PATH.format(item))
                                    .crossfade(true)
                                    .build(),
                                contentDescription = item,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }

                        trigger.move?.uppercase()?.let { move ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = stringResource(R.string.move),
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = move,
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                )
                            }
                        }

                        trigger.minLevel?.toString()?.let { minLevel ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = stringResource(R.string.level, minLevel),
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        trigger.minHappiness?.toString()?.let { minHappiness ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = stringResource(R.string.happiness, minHappiness),
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        trigger.minAffection?.toString()?.let { minAffection ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = stringResource(R.string.min_affection, minAffection),
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        if (trigger.needsOverworldRain) {
                            Text(
                                text = stringResource(R.string.needs_overworld_rain),
                                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            )
                        }

                        trigger.timeOfDay.takeIf { it.orEmpty().isNotEmpty() }?.let { timeOfDay ->
                            Row {
                                Text(
                                    text = stringResource(R.string.time_of_day),
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = timeOfDay.uppercase(),
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                )
                            }
                        }

                        trigger.tradeSpeciesId?.let { tradeSpeciesId ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Trade pokemon:",
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                    fontWeight = FontWeight.Bold
                                )

                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(EndPoints.OFFICIAL_ART_WORK.format(tradeSpeciesId))
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = tradeSpeciesId.toString(),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(50.dp)
                                )
                            }
                        }

                        trigger.location?.uppercase()?.let { location ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = stringResource(R.string.location),
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = location,
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PokemonEvolutionPreview() {
    PreviewTemplate {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            PokemonEvolution(
                name = "Bulbasaur",
                imageUrl = "",
                triggerList = emptyList()
            )
        }
    }
}
