package com.priorDev.pokerroutejc.domain.pokemon.models

import com.priorDev.SearchPokemonNameQuery
import com.priorDev.pokerroutejc.core.getIdFromPokeUrl
import com.priorDev.pokerroutejc.data.network.utils.EndPoints.OFFICIAL_ART_WORK
import com.priorDev.pokerroutejc.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.data.network.pokemon.responses.PokemonNameResponse

data class PokemonNameData(
    val id: Int,
    val name: String,
    var imgUrl: String
)

fun PokemonNameEntity.toDomain() =
    PokemonNameData(
        id = id,
        name = name.uppercase(),
        imgUrl = OFFICIAL_ART_WORK.format(id)
    )

fun PokemonNameResponse.toDomain() =
    PokemonNameData(
        id = url.getIdFromPokeUrl(),
        name = name.uppercase(),
        imgUrl = OFFICIAL_ART_WORK.format(url.getIdFromPokeUrl())
    )

@Suppress("MaxLineLength")
fun PokemonNameData.getAlternativeImg() =
    PokemonNameData(
        id = id,
        name = name.uppercase(),
        imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    )

fun SearchPokemonNameQuery.Pokemon_v2_pokemon.toDomain() =
    PokemonNameData(
        id = id,
        name = name,
        imgUrl = OFFICIAL_ART_WORK.format(id)
    )
