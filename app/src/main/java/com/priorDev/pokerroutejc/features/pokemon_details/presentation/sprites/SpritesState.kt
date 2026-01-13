package com.priorDev.pokerroutejc.features.pokemon_details.presentation.sprites

import com.priorDev.pokerroutejc.core.domain.pokemon.models.SpritesData

data class SpritesState(
    val sprites: SpritesData = SpritesData(),
    val name: String = ""
)
