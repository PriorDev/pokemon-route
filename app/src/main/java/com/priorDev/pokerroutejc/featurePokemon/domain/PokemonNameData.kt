package com.priorDev.pokerroutejc.featurePokemon.domain

import com.priorDev.pokerroutejc.core.getIdFromPokeUrl
import com.priorDev.pokerroutejc.featurePokemon.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.featurePokemon.data.network.response.PokemonNameResponse

data class PokemonNameData(
    val id: Int,
    val name: String,
    var imgUrl: String
)

@Suppress("MaxLineLength")
fun PokemonNameEntity.toDomain() =
    PokemonNameData(
        id = id,
        name = name.uppercase(),
        imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    )

@Suppress("MaxLineLength")
fun PokemonNameResponse.toDomain() =
    PokemonNameData(
        id = url.getIdFromPokeUrl(),
        name = name.uppercase(),
        imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${url.getIdFromPokeUrl()}.png"
    )

@Suppress("MaxLineLength")
fun PokemonNameData.getAlternativeImg() =
    PokemonNameData(
        id = id,
        name = name.uppercase(),
        imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    )
