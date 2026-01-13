package com.priorDev.pokerroutejc.features.pokedex.presentation

import com.priorDev.pokerroutejc.core.domain.pokedex.models.PokedexEntriesData
import com.priorDev.pokerroutejc.core.domain.pokedex.models.PokedexStatus
import com.priorDev.pokerroutejc.core.presentation.components.ErrorState
import com.priorDev.pokerroutejc.core.presentation.components.LoadingIndicator

data class PokedexStates(
    val loading: LoadingIndicator = LoadingIndicator.None,
    val errorState: ErrorState? = null,
    val pokedexStatus: PokedexStatus = PokedexStatus.INCOMPLETE,
    val pokedexName: String = "",
    val entries: List<PokedexEntriesData> = emptyList()
)
