package com.priorDev.pokerroutejc.presentation.pokemonDetails.evolution

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionTriggerResponse
import com.priorDev.pokerroutejc.data.network.utils.EndPoints
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsStates
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate

@Composable
fun EvolutionChainView(
    states: PokemonDetailsStates,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        states.evolutions.forEach { (_, evolutionList) ->
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(evolutionList) { evolution ->
                        PokemonEvolution(
                            name = evolution.name,
                            imageUrl = EndPoints.OFFICIAL_ART_WORK.format(evolution.specieId),
                            triggerList = evolution.evolutionTriggerResponse
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EvolutionChainPreview() {
    PreviewTemplate {
        EvolutionChainView(
            states = PokemonDetailsStates(
                evolutions = mapOf(
                    1 to listOf(
                        EvolutionResponse(
                            specieId = 1,
                            name = "bulbasaur",
                            evolvesFromSpecieId = null,
                            evolutionTriggerResponse = listOf(
                                EvolutionTriggerResponse(
                                    minLevel = 16,
                                    minHappiness = null,
                                    minAffection = null,
                                    needsOverworldRain = false,
                                    timeOfDay = null,
                                    tradeSpeciesId = null,
                                    item = null,
                                    move = null,
                                    location = null
                                )
                            )
                        )
                    )
                )
            )
        )
    }
}
