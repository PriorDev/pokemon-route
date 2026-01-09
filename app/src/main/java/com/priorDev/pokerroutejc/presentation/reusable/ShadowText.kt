package com.priorDev.pokerroutejc.presentation.reusable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ShadowText(
    text: String,
    overflow: TextOverflow,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    shadowColor: Color = MaterialTheme.colorScheme.background,
    color: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = style.copy(
            shadow = Shadow(
                color = shadowColor,
                offset = Offset(4f, 4f),
                blurRadius = 16f
            )
        ),
        color = color,
        modifier = modifier,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines
    )
}
