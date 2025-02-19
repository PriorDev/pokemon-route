package com.priorDev.pokerroutejc.presentation.pokemonDetails

import com.priorDev.pokerroutejc.data.network.pokemon.responses.EvolutionResponse
import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonData
import com.priorDev.pokerroutejc.domain.types.models.DamageRelationsData
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.MoveFilterModel

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
    val evolutions: Map<Int?, List<EvolutionResponse>> = emptyMap(),
    val moveFilters: List<MoveFilterModel> = emptyList(),
    val selectedMove: MoveDetailsData? = null
)
