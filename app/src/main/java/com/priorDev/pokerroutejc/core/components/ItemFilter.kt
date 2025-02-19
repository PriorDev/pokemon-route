package com.priorDev.pokerroutejc.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ItemFilter(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedBackGroundColor: Color = MaterialTheme.colors.primary,
    selectedTextColor: Color = MaterialTheme.colors.onPrimary
) {
    Card(
        backgroundColor = if (isSelected) selectedBackGroundColor else MaterialTheme.colors.background,
        elevation = 2.dp,
        modifier = modifier
            .clickable { onClick() }
    ) {
        Text(
            text = text.uppercase(),
            maxLines = 1,
            color = if (isSelected) selectedTextColor else MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}
