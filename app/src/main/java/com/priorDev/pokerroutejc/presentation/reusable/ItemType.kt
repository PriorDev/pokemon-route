package com.priorDev.pokerroutejc.presentation.reusable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.core.getTypeColor
import com.priorDev.pokerroutejc.domain.types.models.TypeData

@Composable
fun ItemType(
    type: TypeData,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    onClick: () -> Unit = { }
) {
    Box(
        modifier = modifier
            .background(type.id.getTypeColor())
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = type.name.uppercase(),
            style = style,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
