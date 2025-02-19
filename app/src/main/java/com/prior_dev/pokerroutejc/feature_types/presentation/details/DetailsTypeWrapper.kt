package com.prior_dev.pokerroutejc.feature_types.presentation.details

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.prior_dev.pokerroutejc.core.routes.RoutesPokemon

fun NavGraphBuilder.detailsTypeWrapper(route: String) {
    composable(
        route = route,
        arguments = listOf(
            navArgument(name = RoutesPokemon.TypeDetails.argType){
                type = NavType.IntType
            }
        )
    ){
        val viewModel: DetailsTypeViewModel = hiltViewModel()
        val states = viewModel.states.collectAsStateWithLifecycle()
        val details = viewModel.details.collectAsStateWithLifecycle()
        DetailsTypeView(
            states = states.value,
            details = details.value,
            onEvents = viewModel::onEvent
        )
    }
}