package com.priorDev.pokerroutejc.features.pokedex_selection.presentation

import com.priorDev.pokerroutejc.core.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.core.presentation.components.ErrorState
import com.priorDev.pokerroutejc.core.presentation.components.LoadingIndicator
import com.priorDev.pokerroutejc.core.presentation.SortOrder

data class VersionGroupStates(
    val loading: LoadingIndicator = LoadingIndicator.None,
    val errorState: ErrorState? = null,
    val versionGroupList: Map<String, List<VersionGroupsData>> = emptyMap(),
    val sortOrder: SortOrder = SortOrder.Descending
)
