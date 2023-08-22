package com.prior_dev.pokerroutejc.feature_pokemon.presentation.details

sealed class PokemonDetailsUiEvents{
    class OpenTypeDetails(val typeId: Int): PokemonDetailsUiEvents()
}
