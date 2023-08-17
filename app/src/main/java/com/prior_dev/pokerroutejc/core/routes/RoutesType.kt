package com.prior_dev.pokerroutejc.core.routes

sealed class RoutesType(val route: String){
    object TypesList: RoutesType("List")
    object TypeDetails: RoutesType("TypeDetails/{type}"){
        const val argType = "type"

        fun getRoute(type: Int) = "TypeDetails/$type"
    }

    companion object {
        const val ROUTE_NAME = "RoutesType"
    }
}
