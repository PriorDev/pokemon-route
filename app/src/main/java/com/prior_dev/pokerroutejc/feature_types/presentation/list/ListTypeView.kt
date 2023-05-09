package com.prior_dev.pokerroutejc.feature_types.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.components.CommonStatesView
import com.prior_dev.pokerroutejc.core.components.ItemType
import com.prior_dev.pokerroutejc.feature_types.presentation.RoutesType

@Composable
fun ListTypeView(
    nav: NavHostController,
    viewModel: ListTypeViewModel = hiltViewModel(),
) {
    val states by viewModel.states.observeAsState(CommonStates())
    CommonStatesView(onDismiss = viewModel::onDismiss, states = states)
    if(states.isLoading)
        return

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        items(viewModel.types){ type ->
            ItemType(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                type = type,
                style = MaterialTheme.typography.h4,
                onClick = {
                    nav.navigate(RoutesType.TypeDetails.getRoute(type.id))
                }
            )
        }
    }
}