package com.prior_dev.pokerroutejc.feature_pokemon.presentation.details

sealed class PokemonDetailsEvents{
    object OnDismiss: PokemonDetailsEvents()
    class  OnGenerationSelect(val generation: String): PokemonDetailsEvents()
    class  OnTypeSelect(val typeId: Int): PokemonDetailsEvents()
    object OnToggleFilterVisibility: PokemonDetailsEvents()
    class OnAbilityClick(val ability: String) : PokemonDetailsEvents()
    class OnSearchTextChange(val text: String) : PokemonDetailsEvents()

    object OnAbilityDismiss: PokemonDetailsEvents()
}
