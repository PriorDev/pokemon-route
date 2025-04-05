package com.priorDev.pokerroutejc.domain

import com.priorDev.pokerroutejc.domain.pokemon.models.AbilityData
import com.priorDev.pokerroutejc.domain.pokemon.models.PokemonData
import com.priorDev.pokerroutejc.domain.pokemon.models.SpritesData

fun pokemonData() = PokemonData(
    id = 1,
    name = "Totodile",
    sprites = SpritesData(),
    abilities = listOf(
        AbilityData(),
        AbilityData(),
    ),
    stats = emptyList(),
    types = emptyList()
)
