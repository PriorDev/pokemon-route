package com.priorDev.pokerroutejc.core.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.priorDev.pokerroutejc.core.presentation.UiMessages

@Deprecated(
    message = "DisposableMessage is deprecated",
    replaceWith = ReplaceWith("CustomAlertDialog")
)
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
