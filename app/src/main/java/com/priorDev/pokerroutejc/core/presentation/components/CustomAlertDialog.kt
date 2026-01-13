package com.priorDev.pokerroutejc.core.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.priorDev.pokerroutejc.core.presentation.components.PreviewTemplate

@Composable
fun CustomAlertDialog(
    alertDialogModel: AlertDialogModel
) {
    if (alertDialogModel.isVisible.not()) return

    AlertDialog(
        title = {
            Text(
                text = alertDialogModel.title.asString()
            )
        },
        text = {
            Text(text = alertDialogModel.message.asString())
        },
        confirmButton = {
            Button(
                onClick = { alertDialogModel.onConfirm() }
            ) {
                alertDialogModel.confirmText?.let { uiMessages ->
                    Text(
                        text = uiMessages.asString()
                    )
                }
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.outlinedButtonColors(),
                onClick = {
                    alertDialogModel.onDismiss?.invoke()
                }
            ) {
                alertDialogModel.dismissText?.let { uiMessages ->
                    Text(
                        text = uiMessages.asString()
                    )
                }
            }
        },
        onDismissRequest = {
            alertDialogModel.onDismiss?.invoke()
        },
    )
}

@Composable
@Preview
private fun CustomAlertDialogPreview() {
    PreviewTemplate {
        CustomAlertDialog(
            AlertDialogModel(
                isVisible = true,
                title = UiMessages.DynamicMessage("Title"),
                message = UiMessages.DynamicMessage("Message"),
                confirmText = UiMessages.DynamicMessage("Confirm"),
                onConfirm = {},
                dismissText = UiMessages.DynamicMessage("Dismiss"),
            )
        )
    }
}
