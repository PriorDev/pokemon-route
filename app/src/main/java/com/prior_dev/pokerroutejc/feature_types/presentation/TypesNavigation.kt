package com.prior_dev.pokerroutejc.feature_types.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.prior_dev.pokerroutejc.core.routes.RoutesPokemon
import com.prior_dev.pokerroutejc.core.routes.RoutesType
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeView
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeViewModel
import com.prior_dev.pokerroutejc.feature_types.presentation.details.detailsTypeWrapper
import com.prior_dev.pokerroutejc.feature_types.presentation.list.ListTypeView
import com.prior_dev.pokerroutejc.feature_types.presentation.list.ListTypeViewModel
import com.prior_dev.pokerroutejc.feature_types.presentation.list.typeListWrapper

fun NavGraphBuilder.typesNavigation(){
    navigation(
        startDestination = RoutesType.TypesList.route,
        route = RoutesType.ROUTE_NAME
    ){
        typeListWrapper()

        detailsTypeWrapper(RoutesType.TypeDetails.route)
    }
}