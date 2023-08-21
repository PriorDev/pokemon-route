package com.prior_dev.pokerroutejc.core.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DisposableMessage(
    message: String?,
    onDismiss: () -> Unit
) {
    message?.let {
        if(it.isNotEmpty()){
            AlertDialog(
                onDismissRequest = { onDismiss() },
                confirmButton = { },
                text = {
                    Text(text = it)
                },
            )
        }
    }
}