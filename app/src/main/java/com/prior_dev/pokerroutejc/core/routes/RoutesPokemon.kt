package com.prior_dev.pokerroutejc.core.routes

sealed class RoutesPokemon(val route: String){
    object SearchRoute: RoutesPokemon("SearchView")
    object PokemonDetails: RoutesPokemon("DetailsView/{pokemonName}"){
        const val argPokemonName = "pokemonName"

        fun getRoute(pokemon: String) = "DetailsView/$pokemon"
    }

    object TypeDetails: RoutesPokemon("Details/{type}"){
        const val argType = "type"

        fun getRoute(type: Int) = "Details/$type"
    }

    companion object{
        const val ROUTE_NAME = "PokemonRoutes"
    }
}
