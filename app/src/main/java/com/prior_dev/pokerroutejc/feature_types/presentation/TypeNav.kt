package com.prior_dev.pokerroutejc.feature_types.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeView
import com.prior_dev.pokerroutejc.feature_types.presentation.list.ListTypeView

@Composable
fun TypeNav(
    navMenu: NavHostController
) {
    val navTypes = rememberNavController()
    NavHost(
        startDestination = RoutesType.TypesList.route,
        navController = navTypes,
        route = RoutesType.ROUTE_NAME
    ){
        composable(RoutesType.TypesList.route){ ListTypeView(navTypes) }

        composable(
            route = RoutesType.TypeDetails.route,
            arguments = listOf(
                navArgument(RoutesType.TypeDetails.argType){
                    type = NavType.IntType
                }
            )
        ){
            DetailsTypeView()
        }
    }
}