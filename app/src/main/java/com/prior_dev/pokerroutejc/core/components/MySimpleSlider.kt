package com.prior_dev.pokerroutejc.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MySimpleSlider(
    modifier: Modifier = Modifier,
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float> = 1f..250f,
    onValueChange: (Float) -> Unit = { },
    enable: Boolean = false,
    disableColor: Color = MaterialTheme.colors.onBackground
){
    Column(modifier) {
        Text(
            text = "$label:     $value",
            style = MaterialTheme.typography.body1,
            color = disableColor
        )

        Slider(
            value = value,
            onValueChange = { onValueChange(it) },
            valueRange = valueRange,
            enabled = enable,
            colors = SliderDefaults.colors(
                disabledActiveTrackColor = disableColor,
                disabledThumbColor = disableColor
            )
        )
    }
}