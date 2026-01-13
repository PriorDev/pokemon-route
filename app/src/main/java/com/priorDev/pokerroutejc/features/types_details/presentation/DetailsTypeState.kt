package com.priorDev.pokerroutejc.features.types_details.presentation

import com.priorDev.pokerroutejc.core.domain.types.models.TypeDetailsData
import com.priorDev.pokerroutejc.core.presentation.UiMessages

data class DetailsTypeState(
    val isLoading: Boolean = true,
    val uiMessages: UiMessages? = null,
    val details: TypeDetailsData = TypeDetailsData()
)
