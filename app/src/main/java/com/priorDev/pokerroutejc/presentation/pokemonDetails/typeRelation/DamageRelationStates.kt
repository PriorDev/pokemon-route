package com.priorDev.pokerroutejc.presentation.pokemonDetails.typeRelation

import com.priorDev.pokerroutejc.domain.types.models.DamageValue
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.UiMessages

data class DamageRelationStates(
    val damageRelations: Map<UiMessages, List<DamageValue>> = emptyMap(),
    val loading: LoadingIndicator = LoadingIndicator.None,
    val errorState: ErrorState? = null
)
