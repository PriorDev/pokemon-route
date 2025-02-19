package com.priorDev.pokerroutejc.featurePokemon.presentation.details

import com.priorDev.pokerroutejc.data.network.pokemon.EvolutionResponse
import com.priorDev.pokerroutejc.featurePokemon.domain.AbilityDetailsData
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonData
import com.priorDev.pokerroutejc.featureTypes.domain.DamageRelationsData

data class PokemonDetailsStates(
    val isLoading: Boolean = true,
    val message: String = "",
    val isFiltersExpanded: Boolean = false,
    val selectedTypeId: Int = 0,
    val selectedGeneration: String = "",
    val pokemon: PokemonData = PokemonData(),
    val weaknessesAndStrengths: DamageRelationsData = DamageRelationsData(),
    val visibleAbilityDetails: AbilityDetailsData? = null,
    val isAbilityLoading: Boolean? = null,
    val textSearch: String = "",
    val evolutions: Map<Int?, List<EvolutionResponse>> = emptyMap()
)
