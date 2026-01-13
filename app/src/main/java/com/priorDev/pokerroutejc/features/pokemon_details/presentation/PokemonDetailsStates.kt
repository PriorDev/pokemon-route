package com.priorDev.pokerroutejc.features.pokemon_details.presentation

import com.priorDev.pokerroutejc.core.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.core.domain.pokemon.models.PokemonData
import com.priorDev.pokerroutejc.core.presentation.components.LoadingIndicator
import com.priorDev.pokerroutejc.core.presentation.UiMessages

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
