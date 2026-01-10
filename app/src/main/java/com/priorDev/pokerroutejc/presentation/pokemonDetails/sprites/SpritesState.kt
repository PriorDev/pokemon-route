package com.priorDev.pokerroutejc.presentation.pokemonDetails.sprites

import com.priorDev.pokerroutejc.domain.pokemon.models.SpritesData

data class SpritesState(
    val sprites: SpritesData = SpritesData(),
    val name: String = ""
)
