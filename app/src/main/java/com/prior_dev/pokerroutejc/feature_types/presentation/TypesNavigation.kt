package com.prior_dev.pokerroutejc.feature_types.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.prior_dev.pokerroutejc.core.routes.RoutesType
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeView
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeViewModel
import com.prior_dev.pokerroutejc.feature_types.presentation.list.ListTypeView
import com.prior_dev.pokerroutejc.feature_types.presentation.list.ListTypeViewModel

fun NavGraphBuilder.typesNavigation(){
    navigation(
        startDestination = RoutesType.TypesList.route,
        route = RoutesType.ROUTE_NAME
    ){
        composable(RoutesType.TypesList.route){
            val viewModel = hiltViewModel<ListTypeViewModel>()
            val commonStates = viewModel.commonStates.collectAsStateWithLifecycle()
            val typesList = viewModel.typesList

            ListTypeView(
                commonStates = commonStates.value,
                typeList = typesList,
                onEvent = viewModel::onEvent,
            )
        }

        composable(
            route = RoutesType.TypeDetails.route,
            arguments = listOf(
                navArgument(name = RoutesType.TypeDetails.argType){
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
}