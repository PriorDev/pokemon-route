package com.prior_dev.pokerroutejc.feature_types.presentation.list

import com.prior_dev.pokerroutejc.feature_types.domain.TypeData

data class ListTypeStates(
    val types: List<TypeData> = emptyList(),
)
