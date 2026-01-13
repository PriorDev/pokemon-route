package com.priorDev.pokerroutejc.features.pokemon_details.presentation.moves

import com.priorDev.pokerroutejc.core.domain.types.models.TypeData

data class MoveFilterModel(
    val checked: Boolean,
    val type: TypeData,
)
