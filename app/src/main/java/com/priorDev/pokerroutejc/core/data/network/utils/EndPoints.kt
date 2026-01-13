package com.priorDev.pokerroutejc.core.data.network.utils

@SuppressWarnings("MaxLineLength")
object EndPoints {
    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val TYPES = BASE_URL.plus("type")
    const val POKEMONS = BASE_URL.plus("pokemon")
    const val MOVES = BASE_URL.plus("move")
    const val ABILITY = BASE_URL.plus("ability")

    const val OFFICIAL_ART_WORK = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/%s.png"

    const val ITEM_IMAGE_PATH = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/%s.png"

    const val TYPE_IMAGE_PATH = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/%s.png"

    // Graph QL
    const val QL_BASE_URL = "https://beta.pokeapi.co/graphql/v1beta"
}
