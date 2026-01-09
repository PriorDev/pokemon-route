package com.priorDev.pokerroutejc.presentation.reusable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    selectedBackGroundColor: Color = MaterialTheme.colorScheme.primary,
    selectedTextColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) selectedBackGroundColor else MaterialTheme.colorScheme.background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = modifier
            .clickable { onClick() }
    ) {
        Text(
            text = text.uppercase(),
            maxLines = 1,
            color = if (isSelected) selectedTextColor else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}
