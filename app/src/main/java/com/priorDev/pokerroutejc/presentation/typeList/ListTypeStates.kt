package com.priorDev.pokerroutejc.presentation.typeList

import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator

data class ListTypeStates(
    val loadingIndicator: LoadingIndicator = LoadingIndicator.None,
    val error: ErrorState? = null,
    val typeList: List<TypeData> = emptyList()
)
