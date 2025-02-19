package com.priorDev.pokerroutejc.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.priorDev.pokerroutejc.core.CommonStates

@Composable
fun CommonStatesView(
    onDismiss: () -> Unit,
    commonStates: CommonStates,
) {
    if (commonStates.isLoading) {
        Box(
            Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    DisposableMessage(uiMessages = commonStates.uiMessages, onDismiss = onDismiss)
}
