package com.priorDev.pokerroutejc.presentation.pokemonDetails

import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonData

data class PokemonDetailsStates(
    val isLoading: Boolean = true,
    val message: String = "",
    val isFiltersExpanded: Boolean = false,
    val selectedTypeId: Int = 0,
    val selectedGeneration: String = "",
    val pokemon: PokemonData = PokemonData(),
    val visibleAbilityDetails: AbilityDetailsData? = null,
    val isAbilityLoading: Boolean? = null,
    val textSearch: String = ""
)
