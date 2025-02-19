package com.priorDev.pokerroutejc.presentation.core

data class AlertDialogModel(
    val isVisible: Boolean,
    val title: UiMessages,
    val message: UiMessages,
    val confirmText: UiMessages? = null,
    val onConfirm: () -> Unit,
    val dismissText: UiMessages? = null,
    val onDismiss: (() -> Unit)? = null
)
