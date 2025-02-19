package com.priorDev.pokerroutejc.presentation.pokemonDetails.typeRelation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.presentation.reusable.ItemType
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsStates
import com.priorDev.pokerroutejc.R

@Composable
fun WeaknessesAndStrengthView(
    states: PokemonDetailsStates,
    modifier: Modifier = Modifier
) {
    val weaknessesAndStrengths = states.weaknessesAndStrengths
    val cellCount = 2

    Card(modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(cellCount),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = stringResource(id = R.string.weaknesses),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                )
            }

            if (weaknessesAndStrengths.x4DamageFrom.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_x_4),
                            contentDescription = stringResource(id = R.string.damagex4),
                            modifier = Modifier.size(30.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                items(weaknessesAndStrengths.x4DamageFrom) {
                    ItemType(type = it, modifier = Modifier.padding(8.dp))
                }
            }

            if (weaknessesAndStrengths.doubleDamageFrom.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_x_2),
                            contentDescription = stringResource(id = R.string.damageX2),
                            modifier = Modifier.size(30.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                items(weaknessesAndStrengths.doubleDamageFrom) {
                    ItemType(type = it, modifier = Modifier.padding(8.dp))
                }
            }

            if (weaknessesAndStrengths.halfDamageFrom.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_x_1_2),
                            contentDescription = stringResource(id = R.string.half_damage),
                            modifier = Modifier.size(30.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                items(weaknessesAndStrengths.halfDamageFrom) {
                    ItemType(type = it, modifier = Modifier.padding(8.dp))
                }
            }

            if (weaknessesAndStrengths.x1_4DamageFrom.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_x_1_4),
                            contentDescription = stringResource(id = R.string.damage_x1_4),
                            modifier = Modifier.size(30.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                items(weaknessesAndStrengths.x1_4DamageFrom) {
                    ItemType(type = it, modifier = Modifier.padding(8.dp))
                }
            }

            if (weaknessesAndStrengths.noDamageFrom.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_x_0),
                            contentDescription = stringResource(id = R.string.no_damage),
                            modifier = Modifier.size(30.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                items(weaknessesAndStrengths.noDamageFrom) {
                    ItemType(type = it, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}
