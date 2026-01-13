package com.priorDev.pokerroutejc.features.pokemon_details.presentation.typeRelation

import com.priorDev.pokerroutejc.core.domain.types.models.DamageValue
import com.priorDev.pokerroutejc.core.presentation.components.ErrorState
import com.priorDev.pokerroutejc.core.presentation.components.LoadingIndicator
import com.priorDev.pokerroutejc.core.presentation.UiMessages

data class DamageRelationStates(
    val damageRelations: Map<UiMessages, List<DamageValue>> = emptyMap(),
    val loading: LoadingIndicator = LoadingIndicator.None,
    val errorState: ErrorState? = null
)
