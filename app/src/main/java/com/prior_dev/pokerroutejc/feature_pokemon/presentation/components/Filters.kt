package com.prior_dev.pokerroutejc.feature_pokemon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prior_dev.pokerroutejc.R
import com.prior_dev.pokerroutejc.core.components.ItemFilter
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.details.PokemonDetailsViewModel
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.utils.MoveViewStates
import com.prior_dev.pokerroutejc.feature_types.domain.getColor

@Composable
fun Filters() {
    val viewModel: PokemonDetailsViewModel = hiltViewModel()
    val moveStates by viewModel.moveStates.observeAsState(MoveViewStates())

    val types = viewModel.moves.map {
        it.type
    }.distinct()

    val generations = viewModel.moves.map {
        it.generationName
    }.distinct()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(8.dp)
        ){
            item{
                ItemFilter(
                    text = stringResource(id = R.string.all),
                    isSelected = moveStates.selectedTypeId == 0,
                ) {
                    viewModel.onTypeSelect(0)
                }
            }
            items(types){ typeNullable ->
                typeNullable?.let { type ->
                    ItemFilter(
                        text = type.name,
                        isSelected = type.id == moveStates.selectedTypeId,
                        selectedBackGroundColor = type.getColor(),
                        selectedTextColor = Color.Black
                    ) {
                        viewModel.onTypeSelect(type.id)
                    }
                }
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(8.dp)
        ){
            item {
                ItemFilter(
                    text = stringResource(id = R.string.all),
                    isSelected = moveStates.selectedGeneration == "",
                ) {
                    viewModel.onGenerationSelect("")
                }
            }
            items(generations){ generations ->
                ItemFilter(
                    text = generations,
                    isSelected = generations == moveStates.selectedGeneration,
                ) {
                    viewModel.onGenerationSelect(generations)
                }
            }
        }
    }
}