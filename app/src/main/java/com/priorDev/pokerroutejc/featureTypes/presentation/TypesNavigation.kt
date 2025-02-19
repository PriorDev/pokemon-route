package com.priorDev.pokerroutejc.featureTypes.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.priorDev.pokerroutejc.core.routes.RoutesType
import com.priorDev.pokerroutejc.featureTypes.presentation.details.detailsTypeWrapper
import com.priorDev.pokerroutejc.featureTypes.presentation.list.typeListWrapper

fun NavGraphBuilder.typesNavigation() {
    navigation(
        startDestination = RoutesType.TypesList.route,
        route = RoutesType.ROUTE_NAME
    ) {
        typeListWrapper()

        detailsTypeWrapper(RoutesType.TypeDetails.route)
    }
}
