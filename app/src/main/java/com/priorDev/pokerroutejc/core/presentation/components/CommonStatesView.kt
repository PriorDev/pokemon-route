package com.priorDev.pokerroutejc.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.priorDev.pokerroutejc.core.presentation.CommonStates

@Deprecated(
    message = "CommonStatesView is deprecated",
    replaceWith = ReplaceWith("ScreenTemplate")
)
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
