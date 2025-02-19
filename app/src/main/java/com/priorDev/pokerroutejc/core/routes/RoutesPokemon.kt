package com.priorDev.pokerroutejc.core.routes

sealed class RoutesPokemon(val route: String) {
    data object PokemonListRoute : RoutesPokemon("SearchView")
    data object PokemonDetails : RoutesPokemon("DetailsView/{pokemonName}") {
        const val argPokemonName = "pokemonName"

        fun getRoute(pokemon: String) = "DetailsView/$pokemon"
    }

    data object TypeDetails : RoutesPokemon("Details/{type}") {
        const val argType = "type"

        fun getRoute(type: Int) = "Details/$type"
    }

    data object PkSearch : RoutesPokemon("Search")

    companion object {
        const val ROUTE_NAME = "PokemonRoutes"
    }
}
