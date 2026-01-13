package com.priorDev.pokerroutejc.features.pokemon_details.presentation.moves

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterBox(
    filters: List<MoveFilterModel>,
    onDismiss: () -> Unit,
    expandedFilters: Boolean,
    onItemClick: (MoveFilterModel) -> Unit
) {
    if (expandedFilters) {
        Box(
            Modifier
                .fillMaxSize()
                .clickable { onDismiss.invoke() }
                .background(
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
                )
        ) {
            Card(
                modifier = Modifier
                    .padding(bottom = 90.dp)
                    .align(Alignment.TopEnd)
                    .clickable { },
                elevation = CardDefaults.elevatedCardElevation(disabledElevation = 6.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .background(color = FloatingActionButtonDefaults.containerColor)
                ) {
                    items(filters) { filter ->
                        Row(
                            modifier = Modifier
                                .padding(end = 90.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = filter.checked,
                                onCheckedChange = {
                                    onItemClick(filter)
                                }
                            )
                            Text(text = filter.type.name.uppercase())
                        }
                    }
                }
            }
        }
    }
}
