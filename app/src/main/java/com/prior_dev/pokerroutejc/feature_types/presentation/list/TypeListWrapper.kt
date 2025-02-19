package com.prior_dev.pokerroutejc.feature_types.presentation.list

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.prior_dev.pokerroutejc.core.routes.RoutesType

fun NavGraphBuilder.typeListWrapper() {
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
}