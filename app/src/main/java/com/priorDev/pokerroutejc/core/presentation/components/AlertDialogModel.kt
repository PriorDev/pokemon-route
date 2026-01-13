package com.priorDev.pokerroutejc.core.presentation.components

data class AlertDialogModel(
    val isVisible: Boolean = false,
    val title: UiMessages = UiMessages.DynamicMessage(""),
    val message: UiMessages = UiMessages.DynamicMessage(""),
    val confirmText: UiMessages? = null,
    val onConfirm: () -> Unit = { },
    val dismissText: UiMessages? = null,
    val onDismiss: (() -> Unit)? = null
)
