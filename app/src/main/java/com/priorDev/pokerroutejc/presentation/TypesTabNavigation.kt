package com.priorDev.pokerroutejc.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.presentation.typeDetails.detailsTypeWrapper
import com.priorDev.pokerroutejc.presentation.typeList.typeListWrapper

fun NavGraphBuilder.typesNavigation() {
    navigation<Routes.TypeNav>(
        startDestination = Routes.TypesList
    ) {
        typeListWrapper()

        detailsTypeWrapper<Routes.TypeDetails.TypeTab>()
    }
}
