package com.priorDev.pokerroutejc.featureTypes.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.presentation.reusable.CommonStatesView
import com.priorDev.pokerroutejc.presentation.reusable.ItemType
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate
import com.priorDev.pokerroutejc.utils.Routes
import com.priorDev.pokerroutejc.featureTypes.domain.TypeData
import com.priorDev.pokerroutejc.utils.GlobalEventChannel

@Composable
fun ListTypeView(
    commonStates: CommonStates,
    typeList: List<TypeData>,
    onEvent: (ListTypesEvent) -> Unit
) {
    CommonStatesView(onDismiss = { onEvent(ListTypesEvent.OnDismiss) }, commonStates = commonStates)
    if (commonStates.isLoading) return

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(typeList) { type ->
            ItemType(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                type = type,
                style = MaterialTheme.typography.titleLarge,
                onClick = {
                    GlobalEventChannel.navigate(
                        route = Routes.TypeDetails.TypeTab(type.id)
                    )
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun ListTypeViewPreview() {
    val types = listOf(
        TypeData(1, "Rock"),
        TypeData(1, "Agua"),
        TypeData(1, "Dragon"),
        TypeData(1, "Fuego"),
        TypeData(1, "Hielo"),
        TypeData(1, "Fantasma"),
        TypeData(1, "Veneno"),
    )

    PreviewTemplate {
        ListTypeView(
            commonStates = CommonStates(isLoading = false),
            typeList = types,
            onEvent = { }
        )
    }
}
