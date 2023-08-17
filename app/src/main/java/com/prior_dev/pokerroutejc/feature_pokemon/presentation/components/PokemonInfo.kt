package com.prior_dev.pokerroutejc.feature_pokemon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prior_dev.pokerroutejc.R
import com.prior_dev.pokerroutejc.core.components.ItemType
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonData
import com.prior_dev.pokerroutejc.core.routes.RoutesPokemon

@Composable
fun PokemonInfo(
    modifier: Modifier = Modifier,
    pokemon: PokemonData,
    navPokemon: NavHostController,
    cardPadding: PaddingValues,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .verticalScroll(scrollState)
    ){
        Column {
            Spacer(modifier = Modifier.height(100.dp))
            Card(
                modifier = Modifier.padding(cardPadding)
            ) {
                Column{
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
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        pokemon.types.forEach{
                            ItemType(
                                type = it,
                                modifier = Modifier
                                    .width(100.dp)
                            ){
                                navPokemon.navigate(RoutesPokemon.TypeDetails.getRoute(it.id))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(id = R.string.abilities),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        textAlign = TextAlign.Center
                    )
                    pokemon.abilities.filter { !it.isHidden }.forEach{ ability ->
                        Text(
                            text = ability.name,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp)
                                .background(MaterialTheme.colors.background),
                            textAlign = TextAlign.Center,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(id = R.string.hidden_ability),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        textAlign = TextAlign.Center
                    )
                    pokemon.abilities.filter { it.isHidden }.forEach{ ability ->
                        Text(
                            text = ability.name,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp)
                                .background(MaterialTheme.colors.background),
                            textAlign = TextAlign.Center,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(id = R.string.stats),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        textAlign = TextAlign.Center
                    )

                    Column(modifier = Modifier.padding(horizontal = 8.dp)){

                        pokemon.stats.forEach{ stat ->
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