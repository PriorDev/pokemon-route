package com.priorDev.pokerroutejc.presentation.pokemonDetails.pkInfo

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.priorDev.pokerroutejc.core.EnumColorTypes
import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityData
import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonData
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.getColor
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsEvents
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsStates
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate
import com.priorDev.pokerroutejc.ui.Routes

@Composable
fun PokemonInfo(
    states: PokemonDetailsStates,
    onEvents: (PokemonDetailsEvents) -> Unit,
    cardPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val pokemon = states.pokemon
    val colorTypes = if (states.pokemon.types.isEmpty()) {
        listOf(
            EnumColorTypes.Normal.color,
            EnumColorTypes.Normal.color,
        )
    } else {
        listOf(
            states.pokemon.types.first().getColor(),
            states.pokemon.types.last().getColor()
        )
    }

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
            .background(Brush.linearGradient(colorTypes))
    ) {
        Column {
            Spacer(modifier = Modifier.height(100.dp))
            Card(
                modifier = Modifier.padding(cardPadding),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(110.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        pokemon.types.forEach { type ->
                            Button(
                                onClick = {
                                    onEvents(
                                        PokemonDetailsEvents.Navigate(
                                            Routes.TypeDetails.PokemonTab(type.id)
                                        )
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = type.getColor(),
                                    contentColor = Color.Black
                                ),
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
        style = MaterialTheme.typography.headlineMedium,
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
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center
            )

            HorizontalDivider(Modifier.fillMaxWidth(.8f))

            pokemon.abilities.filter { !it.isHidden }.forEach { ability ->
                val color = pokemon.types.first().getColor()
                Button(
                    onClick = {
                        onEvent(PokemonDetailsEvents.OnAbilityClick(ability.name))
                    },
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
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
            )

            Divider(Modifier.fillMaxWidth(.8f))

            pokemon.abilities.filter { it.isHidden }.forEach { ability ->
                val color = pokemon.types.first().getColor()
                Button(
                    onClick = {
                        onEvent(PokemonDetailsEvents.OnAbilityClick(ability.name))
                    },
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
