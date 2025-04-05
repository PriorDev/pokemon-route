package com.priorDev.pokerroutejc.presentation.versionGroups

import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator

data class VersionGroupStates(
    val loading: LoadingIndicator = LoadingIndicator.None,
    val errorState: ErrorState? = null
)
