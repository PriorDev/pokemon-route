package com.prior_dev.pokerroutejc.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData
import com.prior_dev.pokerroutejc.feature_types.domain.getColor

@Composable
fun ItemType(
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.body1,
    type: TypeData,
    onClick: () -> Unit = { },
    elevation: Dp = 16.dp
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        backgroundColor = type.getColor(),
        elevation = elevation,
    ){
        Box(
            modifier = Modifier.padding(4.dp)
        ){
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = type.name.uppercase(),
                style = style,
                color = Color.Black
            )
        }
    }
}