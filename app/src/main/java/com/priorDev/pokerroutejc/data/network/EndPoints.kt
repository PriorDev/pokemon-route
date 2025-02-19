package com.priorDev.pokerroutejc.data.network

object EndPoints {
    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val TYPES = BASE_URL.plus("type")

    const val IMAGE_PATH = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites" +
        "/pokemon/other/official-artwork/%s.png"

    const val ITEM_IMAGE_PATH = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/%s.png"

    // Graph QL
    const val QL_BASE_URL = "https://beta.pokeapi.co/graphql/v1beta"
}
