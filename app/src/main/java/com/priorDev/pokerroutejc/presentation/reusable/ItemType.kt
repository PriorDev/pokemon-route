package com.priorDev.pokerroutejc.presentation.reusable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.getColor

@Composable
fun ItemType(
    type: TypeData,
    modifier: Modifier = Modifier,
    elevation: Dp = 16.dp,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    onClick: () -> Unit = { }
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = type.getColor()),
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = type.name.uppercase(),
                style = style,
                color = Color.Black
            )
        }
    }
}
