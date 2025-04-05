package com.priorDev.pokerroutejc.presentation.pokedex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.priorDev.pokerroutejc.domain.pokedex.models.PokedexEntriesData
import com.priorDev.pokerroutejc.presentation.reusable.ItemType

@Composable
fun PokedexEntryDialog(
    onDismissRequest: () -> Unit,
    selectedEntry: PokedexEntriesData?
) {
    selectedEntry?.let { entry ->
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerLowest,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    Text(
                        text = "#${entry.entryNumber}",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }

                AsyncImage(
                    model = entry.pokemonImage,
                    contentDescription = null
                )

                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerLowest,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    Text(
                        text = entry.pokemonName,
                        style = MaterialTheme.typography.headlineLarge,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    entry.types.forEach { type ->
                        ItemType(
                            type = type,
                            modifier = Modifier.weight(1f)
                                .height(46.dp)
                        )
                    }
                }
            }
        }
    }
}