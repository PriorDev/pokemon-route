package com.priorDev.pokerroutejc.features.pokemon_details.presentation.moves

import com.priorDev.pokerroutejc.core.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.core.presentation.components.ErrorState
import com.priorDev.pokerroutejc.core.presentation.components.LoadingIndicator

data class PokemonMovesState(
    val selectedMove: MoveDetailsData? = null,
    val moveCriteria: List<MoveFilterModel> = emptyList(),
    val loading: LoadingIndicator = LoadingIndicator.None,
    val errorState: ErrorState? = null,
)
