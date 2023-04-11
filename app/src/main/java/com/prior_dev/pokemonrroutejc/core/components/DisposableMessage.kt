package com.prior_dev.pokemonrroutejc.core.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DisposableMessage(
    message: String,
    onDismiss: () -> Unit
) {
    if(message.isNotEmpty()){
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = { },
            text = {
                Text(text = message)
            },
        )
    }
}