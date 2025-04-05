package com.priorDev.pokerroutejc.domain.pokedex.models

import com.priorDev.GetPokedexEntriesQuery

data class PokedexData(
    val pokedexName: String = "",
    val status: PokedexStatus = PokedexStatus.INCOMPLETE,
    val entries: List<PokedexEntriesData> = emptyList()
)

fun GetPokedexEntriesQuery.Pokemon_v2_pokedex.toPokedexData(): PokedexData {
    return PokedexData(
        pokedexName = pokemon_v2_pokedexdescriptions
            .firstOrNull()
            ?.description.orEmpty(),
        entries = pokemon_v2_pokemondexnumbers.map { it.toEntryData() },
        status = PokedexStatus.INCOMPLETE
    )
}
