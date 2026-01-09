package com.priorDev.pokerroutejc.presentation.pokemonDetails.sprites

import com.priorDev.pokerroutejc.data.network.pokemon.responses.Sprites

data class SpritesState(
    val sprites: Sprites = Sprites(),
    val name: String = ""
)
