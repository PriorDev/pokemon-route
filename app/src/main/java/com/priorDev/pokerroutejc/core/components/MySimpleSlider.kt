package com.priorDev.pokerroutejc.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MySimpleSlider(
    label: String,
    value: Float,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 1f..250f,
    onValueChange: (Float) -> Unit = { },
    enable: Boolean = false,
    disableColor: Color = MaterialTheme.colors.onBackground
) {
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
