package com.priorDev.pokerroutejc.featurePokemon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.components.ItemFilter
import com.priorDev.pokerroutejc.featurePokemon.domain.MoveDetailsData
import com.priorDev.pokerroutejc.featurePokemon.presentation.details.PokemonDetailsEvents
import com.priorDev.pokerroutejc.featurePokemon.presentation.details.PokemonDetailsStates
import com.priorDev.pokerroutejc.featureTypes.domain.TypeData
import com.priorDev.pokerroutejc.featureTypes.domain.getColor

@Composable
fun Filters(
    states: PokemonDetailsStates,
    moveList: List<MoveDetailsData>,
    onEvents: (PokemonDetailsEvents) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val types = moveList.map {
        it.type!!
    }.distinct()

    val generations = moveList.map {
        it.generationName
    }.distinct()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
    ) {
        SearchTextField(
            value = states.textSearch,
            onValueChange = { onEvents(PokemonDetailsEvents.OnSearchTextChange(it)) },
            onSearch = { keyboardController?.hide() }
        )

        TypeFilters(states = states, types = types, onEvents = onEvents)

        GenerationFilters(states = states, generations = generations, onEvents = onEvents)
    }
}

@Composable
fun TypeFilters(
    states: PokemonDetailsStates,
    types: List<TypeData>,
    onEvents: (PokemonDetailsEvents) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
        item {
            ItemFilter(
                text = stringResource(id = R.string.all),
                isSelected = states.selectedTypeId == 0,
                onClick = {
                    onEvents(PokemonDetailsEvents.OnTypeSelect(0))
                }
            )
        }
        items(types) { type ->
            ItemFilter(
                text = type.name,
                isSelected = type.id == states.selectedTypeId,
                selectedBackGroundColor = type.getColor(),
                selectedTextColor = Color.Black,
                onClick = {
                    onEvents(PokemonDetailsEvents.OnTypeSelect(type.id))
                }
            )
        }
    }
}

@Composable
fun GenerationFilters(
    states: PokemonDetailsStates,
    generations: List<String>,
    onEvents: (PokemonDetailsEvents) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
        item {
            ItemFilter(
                text = stringResource(id = R.string.all),
                isSelected = states.selectedGeneration == "",
                onClick = {
                    onEvents(PokemonDetailsEvents.OnGenerationSelect(""))
                }
            )
        }
        items(generations) { generations ->
            ItemFilter(
                text = generations,
                isSelected = generations == states.selectedGeneration,
                onClick = {
                    onEvents(PokemonDetailsEvents.OnGenerationSelect(generations))
                }
            )
        }
    }
}
