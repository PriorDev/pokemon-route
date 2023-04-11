package com.prior_dev.pokemonrroutejc.ui

sealed class RoutesMenu(val route: String){
    object NavTypesRoute: RoutesMenu("NavTypes")
    object NavPokemonRoute: RoutesMenu("NavPokemon")

    companion object{
        const val ROUTE_NAME: String = "NavigationMenu"
    }
}

