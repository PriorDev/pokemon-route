package com.prior_dev.pokerroutejc.feature_pokemon.presentation.pokemon_list

sealed class PokemonListEvent {
    class OnListText(val text: String): PokemonListEvent()
    data object OnDismiss: PokemonListEvent()
    data object OnRefresh: PokemonListEvent()
    data object OnSearch: PokemonListEvent()
}
