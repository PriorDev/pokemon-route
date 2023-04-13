package com.prior_dev.pokemonrroutejc.core.components


import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.body1,
    shadowColor: Color = MaterialTheme.colors.background,
    color: Color = MaterialTheme.colors.onBackground,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow,
    maxLines: Int = Int.MAX_VALUE
){
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