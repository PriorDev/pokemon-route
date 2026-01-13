package com.priorDev.pokerroutejc.core.presentation

import com.priorDev.pokerroutejc.core.presentation.UiMessages

@Deprecated(
    message = "CommonStates is deprecated",
    replaceWith = ReplaceWith("ScreenTemplate")
)
data class CommonStates(
    val isLoading: Boolean = true,
    val uiMessages: UiMessages? = null,
    val searchText: String = "",
)
