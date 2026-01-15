package com.priorDev.pokerroutejc.presentation.typeDetails

import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator

data class DetailsTypeState(
    val loadingIndicator: LoadingIndicator = LoadingIndicator.None,
    val details: TypeDetailsData = TypeDetailsData(),
    val errorState: ErrorState<DetailsTypeEvents>? = null
)
