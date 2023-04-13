package com.prior_dev.pokemonrroutejc.feature_pokemon.domain

import com.prior_dev.pokemonrroutejc.core.getIdFromPokeUrl
import com.prior_dev.pokemonrroutejc.feature_pokemon.data.database.PokemonNameEntity
import com.prior_dev.pokemonrroutejc.feature_pokemon.data.network.response.PokemonNameResponse

data class PokemonNameData(
    val id: Int,
    val name: String,
    var imgUrl: String
)

fun PokemonNameEntity.toDomain() =
    PokemonNameData(
        id = id,
        name = name.uppercase(),
        imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    )

fun PokemonNameResponse.toDomain() =
    PokemonNameData(
        id = url.getIdFromPokeUrl(),
        name = name.uppercase(),
        imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${url.getIdFromPokeUrl()}.png"
    )

fun PokemonNameData.getAlternativeImg() =
    PokemonNameData(
        id = id,
        name = name.uppercase(),
        imgUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    )
