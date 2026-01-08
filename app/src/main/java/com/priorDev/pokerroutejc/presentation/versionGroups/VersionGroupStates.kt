package com.priorDev.pokerroutejc.presentation.versionGroups

import com.priorDev.pokerroutejc.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.SortOrder

data class VersionGroupStates(
    val loading: LoadingIndicator = LoadingIndicator.None,
    val errorState: ErrorState? = null,
    val versionGroupList: Map<String, List<VersionGroupsData>> = emptyMap(),
    val sortOrder: SortOrder = SortOrder.Descending
)
