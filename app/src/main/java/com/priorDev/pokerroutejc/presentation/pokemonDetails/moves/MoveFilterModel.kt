package com.priorDev.pokerroutejc.presentation.pokemonDetails.moves

import com.priorDev.pokerroutejc.domain.types.models.TypeData

data class MoveFilterModel (
    val checked: Boolean,
    val type: TypeData,
)
