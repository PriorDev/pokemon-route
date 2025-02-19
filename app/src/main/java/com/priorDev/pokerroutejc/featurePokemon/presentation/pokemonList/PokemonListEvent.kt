package com.priorDev.pokerroutejc.featurePokemon.presentation.pokemonList

sealed class PokemonListEvent {
    data object OnDismiss : PokemonListEvent()
    data object OnRefresh : PokemonListEvent()
    data object OnSearch : PokemonListEvent()
}
