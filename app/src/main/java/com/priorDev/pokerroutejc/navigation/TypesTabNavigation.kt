package com.priorDev.pokerroutejc.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.priorDev.pokerroutejc.navigation.Routes
import com.priorDev.pokerroutejc.features.types_details.presentation.detailsTypeWrapper
import com.priorDev.pokerroutejc.features.types_list.presentation.typeListWrapper

fun NavGraphBuilder.typesNavigation() {
    navigation<Routes.TypeNav>(
        startDestination = Routes.TypesList
    ) {
        typeListWrapper()

        detailsTypeWrapper<Routes.TypeDetails.TypeTab>()
    }
}
