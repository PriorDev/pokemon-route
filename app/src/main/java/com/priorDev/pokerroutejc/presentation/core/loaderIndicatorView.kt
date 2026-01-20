package com.priorDev.pokerroutejc.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BoxScope.LoaderIndicatorView(
    loadingIndicator: LoadingIndicator,
    modifier: Modifier = Modifier
) {
    when (loadingIndicator) {
        LoadingIndicator.None,

        LoadingIndicator.Refreshing -> {
            // Don't display anything
        }

        LoadingIndicator.SolidSpinningWheel -> {
            Box(
                modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        LoadingIndicator.TopLinear -> {
            LinearProgressIndicator(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
        }
    }
}