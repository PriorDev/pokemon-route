package com.priorDev.pokerroutejc.presentation.pokemonDetails

import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonData
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.UiMessages

data class PokemonDetailsStates(
    val loading: LoadingIndicator = LoadingIndicator.None,
    val uiMessages: UiMessages? = null,
    val isFiltersExpanded: Boolean = false,
    val selectedTypeId: Int = 0,
    val selectedGeneration: String = "",
    val pokemon: PokemonData = PokemonData(),
    val visibleAbilityDetails: AbilityDetailsData? = null,
    val isAbilityLoading: Boolean? = null,
    val textSearch: String = ""
)
