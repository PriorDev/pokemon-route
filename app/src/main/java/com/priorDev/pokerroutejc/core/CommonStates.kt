package com.priorDev.pokerroutejc.core

import com.priorDev.pokerroutejc.presentation.core.UiMessages

@Deprecated(
    message = "CommonStates is deprecated",
    replaceWith = ReplaceWith("ScreenTemplate")
)
data class CommonStates(
    val isLoading: Boolean = true,
    val uiMessages: UiMessages? = null,
    val searchText: String = "",
)
