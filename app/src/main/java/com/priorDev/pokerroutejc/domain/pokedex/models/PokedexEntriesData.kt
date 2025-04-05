package com.priorDev.pokerroutejc.domain.pokedex.models

import com.priorDev.GetPokedexEntriesQuery
import com.priorDev.pokerroutejc.data.network.utils.EndPoints
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.utils.orZero

data class PokedexEntriesData(
    val entryNumber: Int,
    val status: PokedexEntryStatus = PokedexEntryStatus.NOT_CAUGHT,
    val pokemonName: String,
    val pokemonId: Int,
    val pokemonImage: String,
    val types: List<TypeData>
)

fun GetPokedexEntriesQuery.Pokemon_v2_pokemondexnumber.toEntryData(): PokedexEntriesData {
    return PokedexEntriesData(
        entryNumber = pokedex_number,
        pokemonName = pokemon_v2_pokemonspecy
            ?.pokemon_v2_pokemonspeciesnames
            ?.firstOrNull()
            ?.name.orEmpty(),
        pokemonId = pokemon_v2_pokemonspecy?.id.orZero(),
        pokemonImage = EndPoints.OFFICIAL_ART_WORK.format(pokemon_v2_pokemonspecy?.id.orZero()),
        types = pokemon_v2_pokemonspecy
            ?.pokemon_v2_pokemons
            ?.firstOrNull()
            ?.pokemon_v2_pokemontypes
            ?.map {
                TypeData(
                    id = it.pokemon_v2_type?.id.orZero(),
                    name = it.pokemon_v2_type?.name.orEmpty()
                )
            }
            .orEmpty()
    )
}