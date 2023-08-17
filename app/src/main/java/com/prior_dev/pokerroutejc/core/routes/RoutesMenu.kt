package com.prior_dev.pokerroutejc.core.routes

sealed class RoutesMenu(val route: String){
    companion object{
        const val ROUTE_NAME: String = "NavigationMenu"
    }
}

