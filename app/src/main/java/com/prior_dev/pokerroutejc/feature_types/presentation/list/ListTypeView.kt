package com.prior_dev.pokerroutejc.feature_types.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.components.CommonStatesView
import com.prior_dev.pokerroutejc.core.components.ItemType

@Composable
fun ListTypeView(
    commonStates: CommonStates,
    states: ListTypeStates,
    onEvent: (ListTypesEvent) -> Unit,
    onUiEvent: (ListTypesUiEvent.openTypesDetailScreen) -> Unit
) {
    CommonStatesView(onDismiss = { onEvent(ListTypesEvent.onDismiss) }, commonStates = commonStates)
    if(commonStates.isLoading)
        return

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        items(states.types){ type ->
            ItemType(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                type = type,
                style = MaterialTheme.typography.h4,
                onClick = {
                    onUiEvent(ListTypesUiEvent.openTypesDetailScreen(type.id))
                }
            )
        }
    }
}