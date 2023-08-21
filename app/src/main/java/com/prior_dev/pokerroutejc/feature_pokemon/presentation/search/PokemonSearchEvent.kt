package com.prior_dev.pokerroutejc.feature_pokemon.presentation.search

sealed class PokemonSearchEvent{
    class OnSearchText(val text: String): PokemonSearchEvent()
    object onDismiss: PokemonSearchEvent()

    object getNextPage: PokemonSearchEvent()
}
