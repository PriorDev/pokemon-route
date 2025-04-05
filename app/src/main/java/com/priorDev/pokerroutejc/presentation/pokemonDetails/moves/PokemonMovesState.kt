package com.priorDev.pokerroutejc.presentation.pokemonDetails.moves

import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator

data class PokemonMovesState(
    val selectedMove: MoveDetailsData? = null,
    val moveCriteria: List<MoveFilterModel> = emptyList(),
    val loading: LoadingIndicator = LoadingIndicator.None,
    val errorState: ErrorState? = null,
)
