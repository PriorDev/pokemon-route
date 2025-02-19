package com.priorDev.pokerroutejc.featureTypes.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.priorDev.pokerroutejc.utils.Routes
import com.priorDev.pokerroutejc.featureTypes.presentation.details.detailsTypeWrapper
import com.priorDev.pokerroutejc.featureTypes.presentation.list.typeListWrapper

fun NavGraphBuilder.typesNavigation() {
    navigation<Routes.TypeNav>(
        startDestination = Routes.TypesList
    ) {
        typeListWrapper()

        detailsTypeWrapper<Routes.TypeDetails.TypeTab>()
    }
}
