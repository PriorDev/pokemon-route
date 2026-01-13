package com.priorDev.pokerroutejc.features.types_list.presentation

import com.priorDev.pokerroutejc.core.domain.types.models.TypeData
import com.priorDev.pokerroutejc.core.presentation.components.ErrorState
import com.priorDev.pokerroutejc.core.presentation.components.LoadingIndicator

data class ListTypeStates(
    val loadingIndicator: LoadingIndicator = LoadingIndicator.None,
    val error: ErrorState? = null,
    val typeList: List<TypeData> = emptyList()
)
