package com.priorDev.pokerroutejc.core

data class CommonStates(
    val isLoading: Boolean = true,
    val uiMessages: UiMessages? = null,
    val searchText: String = "",
)
