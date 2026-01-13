package com.priorDev.pokerroutejc.features.pokemon_details.presentation.moves

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.features.pokemon_details.presentation.PokemonDetailsEvents

@Composable
fun StickyHeader(
    learnMethod: String,
    moves: List<MoveDetailsData>,
    onEvents: (PokemonDetailsEvents) -> Unit
) {
    val isExpanded by remember(moves) {
        derivedStateOf { moves.any { it.visible } }
    }
    Card(
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = learnMethod,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )

            IconToggleButton(
                checked = isExpanded,
                onCheckedChange = {
                    onEvents(
                        PokemonDetailsEvents.ToggleLearnMethodExpand(
                            learnMethod = learnMethod,
                            isExpanded = it
                        )
                    )
                }
            ) {
                if (isExpanded) {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = stringResource(R.string.filter)
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.filter)
                    )
                }
            }
        }
    }
}
