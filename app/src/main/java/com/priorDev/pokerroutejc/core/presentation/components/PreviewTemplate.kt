package com.priorDev.pokerroutejc.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.priorDev.pokerroutejc.core.presentation.theme.PokemonRRouteJCTheme

@Composable
fun PreviewTemplate(
    content: @Composable () -> Unit = {}
) {
    PokemonRRouteJCTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}
