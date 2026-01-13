package com.priorDev.pokerroutejc.core.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiMessages {
    data class DynamicMessage(val message: String) : UiMessages()
    class StringResource(
        @StringRes val stringId: Int,
        vararg val args: Any
    ) : UiMessages()

    @Suppress("SpreadOperator")
    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicMessage -> message
            is StringResource -> {
                stringResource(id = stringId, *args)
            }
        }
    }
}
