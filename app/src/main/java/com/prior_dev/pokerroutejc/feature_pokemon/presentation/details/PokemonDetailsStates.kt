package com.prior_dev.pokerroutejc.feature_pokemon.presentation.details

import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonData
import com.prior_dev.pokerroutejc.feature_types.domain.DamageRelationsData

data class PokemonDetailsStates(
    val isLoading: Boolean = true,
    val message: String = "",
    val isFiltersExpanded: Boolean = false,
    val selectedTypeId: Int = 0,
    val selectedGeneration: String = "",
    val pokemon: PokemonData = PokemonData(),
    val weaknessesAndStrengths: DamageRelationsData = DamageRelationsData()
)