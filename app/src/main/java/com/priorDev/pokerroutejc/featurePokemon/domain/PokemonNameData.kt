package com.priorDev.pokerroutejc.featurePokemon.domain

import com.priorDev.SearchPokemonNameQuery
import com.priorDev.pokerroutejc.core.getIdFromPokeUrl
import com.priorDev.pokerroutejc.data.network.EndPoints.IMAGE_PATH
import com.priorDev.pokerroutejc.featurePokemon.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.PokemonNameResponse

data class PokemonNameData(
    val id: Int,
    val name: String,
    var imgUrl: String
)

fun PokemonNameEntity.toDomain() =
    PokemonNameData(
        id = id,
        name = name.uppercase(),
        imgUrl = IMAGE_PATH.format(id)
    )

fun PokemonNameResponse.toDomain() =
    PokemonNameData(
        id = url.getIdFromPokeUrl(),
        name = name.uppercase(),
        imgUrl = IMAGE_PATH.format(url.getIdFromPokeUrl())
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
        imgUrl = IMAGE_PATH.format(id)
    )
