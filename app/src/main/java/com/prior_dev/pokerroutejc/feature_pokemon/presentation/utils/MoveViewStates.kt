package com.prior_dev.pokerroutejc.feature_pokemon.presentation.utils

data class MoveViewStates(
    val isLoading: Boolean = true,
    val message: String = "",
    val isFiltersExpanded: Boolean = false,
    val selectedTypeId: Int = 0,
    val selectedGeneration: String = ""
)
