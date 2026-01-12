package com.priorDev.pokerroutejc.presentation.typeDetails

import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData
import com.priorDev.pokerroutejc.presentation.core.UiMessages

data class DetailsTypeState(
    val isLoading: Boolean = true,
    val uiMessages: UiMessages? = null,
    val details: TypeDetailsData = TypeDetailsData()
)
