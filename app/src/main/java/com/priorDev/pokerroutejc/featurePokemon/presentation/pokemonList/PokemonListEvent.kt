package com.priorDev.pokerroutejc.featurePokemon.presentation.pokemonList

sealed class PokemonListEvent {
    class OnListText(val text: String) : PokemonListEvent()
    data object OnDismiss : PokemonListEvent()
    data object OnRefresh : PokemonListEvent()
    data object OnSearch : PokemonListEvent()
}
