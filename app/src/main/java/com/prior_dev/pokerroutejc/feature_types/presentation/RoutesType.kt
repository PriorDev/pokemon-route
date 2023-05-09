package com.prior_dev.pokerroutejc.feature_types.presentation

sealed class RoutesType(val route: String){
    object TypesList: RoutesType("List")
    object TypeDetails: RoutesType("Details/{type}"){
        const val argType = "type"

        fun getRoute(type: Int) = "Details/$type"
    }

    companion object {
        const val ROUTE_NAME = "RoutesType"
    }
}
