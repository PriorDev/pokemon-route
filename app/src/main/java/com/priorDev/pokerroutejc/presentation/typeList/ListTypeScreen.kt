package com.priorDev.pokerroutejc.presentation.typeList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.presentation.core.ScreenStates
import com.priorDev.pokerroutejc.presentation.core.ScreenTemplate
import com.priorDev.pokerroutejc.presentation.reusable.ItemType
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate
import com.priorDev.pokerroutejc.ui.Routes

@Composable
fun ListTypeScreen(
    screenState: ScreenStates,
    typeList: List<TypeData>,
    onEvent: (ListTypesEvent) -> Unit
) {
    ScreenTemplate(
        loadingIndicator = screenState.loadingIndicator,
        errorState = screenState.error,
        dialogModel = screenState.dialogModel,
        onRefresh = {
            onEvent(ListTypesEvent.Refresh)
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 24.dp)
        ) {
            items(typeList) { type ->
                ItemType(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    type = type,
                    style = MaterialTheme.typography.titleLarge,
                    onClick = {
                        onEvent(
                            ListTypesEvent.Navigate(
                                Routes.TypeDetails.TypeTab(type.id)
                            )
                        )
                    }
                )
            }
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
        ListTypeScreen(
            screenState = ScreenStates(),
            typeList = types,
            onEvent = {}
        )
    }
}
