package com.priorDev.pokerroutejc.presentation.utils

import androidx.compose.runtime.Composable
import com.priorDev.pokerroutejc.presentation.core.UiMessages

data class PageItem(
    val index: Int,
    val title: UiMessages,
    val content: @Composable () -> Unit
)
