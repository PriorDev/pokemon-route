package com.priorDev.pokerroutejc.core.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.priorDev.pokerroutejc.core.UiMessages

@Composable
fun DisposableMessage(
    uiMessages: UiMessages?,
    onDismiss: () -> Unit
) {
    uiMessages?.let {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = { },
            text = {
                Text(text = uiMessages.asString())
            },
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DisposableMessagePreview() {
    PreviewTemplate {
        DisposableMessage(uiMessages = UiMessages.DynamicMessage("Mensaje de error")) {}
    }
}
