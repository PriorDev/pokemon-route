package com.priorDev.pokerroutejc.featurePokemon.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.components.PreviewTemplate
import com.priorDev.pokerroutejc.featurePokemon.domain.AbilityData
import com.priorDev.pokerroutejc.featurePokemon.domain.AbilityDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonData
import com.priorDev.pokerroutejc.utils.Routes
import com.priorDev.pokerroutejc.featurePokemon.presentation.details.PokemonDetailsEvents
import com.priorDev.pokerroutejc.featurePokemon.presentation.details.PokemonDetailsStates
import com.priorDev.pokerroutejc.featureTypes.domain.TypeData
import com.priorDev.pokerroutejc.featureTypes.domain.getColor
import com.priorDev.pokerroutejc.utils.GlobalEventChannel

@Composable
fun PokemonInfo(
    states: PokemonDetailsStates,
    onEvents: (PokemonDetailsEvents) -> Unit,
    cardPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val pokemon = states.pokemon

    states.isAbilityLoading?.let { isLoading ->
        AbilityDialog(
            ability = states.visibleAbilityDetails ?: AbilityDetailsData(),
            onDismiss = { onEvents(PokemonDetailsEvents.OnAbilityDismiss) },
            isLoading = isLoading
        )
    }

    Box(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        Column {
            Spacer(modifier = Modifier.height(100.dp))
            Card(
                modifier = Modifier.padding(cardPadding)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(110.dp))

                    Text(
                        text = "#${pokemon.id} ${pokemon.name.uppercase()}",
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        pokemon.types.forEach { type ->
                            Button(
                                onClick = {
                                    GlobalEventChannel.navigate(
                                        Routes.TypeDetails.PokemonTab(type.id)
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = type.getColor()),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                Text(text = type.name.uppercase())
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    PokemonAbilities(pokemon, onEvents)

                    Spacer(modifier = Modifier.height(16.dp))

                    PokemonBasicStats(pokemon)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pokemon.sprites.others?.officialArtFrontDefault ?: pokemon.sprites.frontDefault)
                .crossfade(true)
                .build(),
            contentDescription = pokemon.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun PokemonBasicStats(pokemon: PokemonData) {
    Text(
        text = stringResource(id = R.string.stats),
        style = MaterialTheme.typography.h6,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        textAlign = TextAlign.Center
    )

    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        pokemon.stats.forEach { stat ->
            val animDelay = 100 * pokemon.stats.indexOf(stat)

            BasicStat(
                name = stat.name,
                value = stat.baseStat,
                animDelay = animDelay
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun PokemonAbilities(
    pokemon: PokemonData,
    onEvent: (PokemonDetailsEvents) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.abilities),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center
            )

            Divider(Modifier.fillMaxWidth(.8f))

            pokemon.abilities.filter { !it.isHidden }.forEach { ability ->
                val color = pokemon.types.first().getColor()
                Button(
                    onClick = {
                        onEvent(PokemonDetailsEvents.OnAbilityClick(ability.name))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = color)
                ) {
                    Text(text = ability.name)
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.hidden_ability),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
            )

            Divider(Modifier.fillMaxWidth(.8f))

            pokemon.abilities.filter { it.isHidden }.forEach { ability ->
                val color = pokemon.types.first().getColor()
                Button(
                    onClick = {
                        onEvent(PokemonDetailsEvents.OnAbilityClick(ability.name))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = color)
                ) {
                    Text(text = ability.name)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PokemonInfoPreview() {
    PreviewTemplate {
        val states = PokemonDetailsStates(
            pokemon = PokemonData(
                name = "TOTODILE",
                abilities = listOf(
                    AbilityData("Hidratacion", isHidden = false),
                    AbilityData("Hidratacion", isHidden = false),
                    AbilityData("Absorbe Agua", isHidden = true),
                ),
                types = listOf(
                    TypeData(1, "Normal"),
                    TypeData(1, "Ice"),
                )
            )
        )
        val cardPadding = PaddingValues()
        PokemonInfo(
            states = states,
            onEvents = {},
            cardPadding = cardPadding
        )
    }
}
