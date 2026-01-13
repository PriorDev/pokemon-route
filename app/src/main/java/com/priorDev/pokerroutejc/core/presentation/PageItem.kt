package com.priorDev.pokerroutejc.core.presentation

import com.priorDev.pokerroutejc.core.presentation.UiMessages

data class PageItem(
    val index: Int,
    val title: UiMessages,
    val page: PkDetailsPages,
)

enum class PkDetailsPages {
    EVOLUTION_CHAIN,
    POKEMON_INFO,
    DAMAGE_RELATION,
    POKEMON_MOVES,
    SPRITES
}
