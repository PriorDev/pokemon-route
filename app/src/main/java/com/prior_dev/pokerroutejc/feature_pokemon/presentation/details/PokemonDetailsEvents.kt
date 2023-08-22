package com.prior_dev.pokerroutejc.feature_pokemon.presentation.details

sealed class PokemonDetailsEvents{
    object onDismiss: PokemonDetailsEvents()
    class  onGenerationSelect(val generation: String): PokemonDetailsEvents()
    class  onTypeSelect(val typeId: Int): PokemonDetailsEvents()
    object onToggleFilterVisibility: PokemonDetailsEvents()
}
