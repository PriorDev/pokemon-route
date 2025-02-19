package com.priorDev.pokerroutejc.core.routes

sealed class RoutesMenu(val route: String) {
    companion object {
        const val ROUTE_NAME: String = "NavigationMenu"
    }
}
