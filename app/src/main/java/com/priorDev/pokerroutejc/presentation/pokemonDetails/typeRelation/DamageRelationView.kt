package com.priorDev.pokerroutejc.presentation.pokemonDetails.typeRelation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.presentation.core.ScreenTemplate
import com.priorDev.pokerroutejc.presentation.reusable.ItemType

@Composable
fun DamageRelationView(
    states: DamageRelationStates,
    modifier: Modifier = Modifier
) {
    val damageKeys = states.damageRelations.keys.toList()

    ScreenTemplate(
        modifier = modifier,
        errorState = states.errorState,
        loadingIndicator = states.loading,
        onEvent = { }
    ) {
        LazyColumn {
            items(damageKeys) { key ->
                Card(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = key.asString(),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )

                    Card(
                        modifier = Modifier.padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                        )
                    ) {
                        states.damageRelations[key]?.forEach {
                            ItemType(
                                type = it.type,
                                modifier = Modifier.padding(8.dp),
                                elevation = 0.dp
                            )
                        }
                    }
                }
            }
        }
    }
}
