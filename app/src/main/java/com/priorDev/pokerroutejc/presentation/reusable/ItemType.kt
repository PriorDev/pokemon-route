package com.priorDev.pokerroutejc.presentation.reusable

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.core.getTypeColor
import com.priorDev.pokerroutejc.domain.types.models.TypeData

@Composable
fun ItemType(
    type: TypeData,
    modifier: Modifier = Modifier,
    elevation: Dp? = null,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    onClick: () -> Unit = { }
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = type.id.getTypeColor()
        ),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .padding(8.dp)
            .aspectRatio(1f)
            .clickable { onClick() },
        elevation = elevation?.let { CardDefaults.cardElevation(defaultElevation = elevation) }
            ?: CardDefaults.cardElevation()
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
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
