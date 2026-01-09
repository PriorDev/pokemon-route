package com.priorDev.pokerroutejc.presentation.pokedex

import com.priorDev.pokerroutejc.domain.pokedex.models.PokedexEntriesData
import com.priorDev.pokerroutejc.domain.pokedex.models.PokedexStatus
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator

data class PokedexStates(
    val loading: LoadingIndicator = LoadingIndicator.None,
    val errorState: ErrorState? = null,
    val pokedexStatus: PokedexStatus = PokedexStatus.INCOMPLETE,
    val pokedexName: String = "",
    val entries: List<PokedexEntriesData> = emptyList()
)
