package com.prior_dev.pokerroutejc.feature_pokemon.presentation.details

import androidx.navigation.NavOptionsBuilder

sealed class PokemonDetailsEvents{
    class OnGenerationSelect(val generation: String): PokemonDetailsEvents()

    class OnTypeSelect(val typeId: Int): PokemonDetailsEvents()

    class OnAbilityClick(val ability: String) : PokemonDetailsEvents()

    class OnSearchTextChange(val text: String) : PokemonDetailsEvents()

    object OnDismiss: PokemonDetailsEvents()
    object OnToggleFilterVisibility: PokemonDetailsEvents()

    object OnAbilityDismiss: PokemonDetailsEvents()
}
