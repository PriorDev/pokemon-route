package com.priorDev.pokerroutejc.presentation.pokemonDetails.typeRelation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.domain.types.models.DamageValue
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.presentation.core.ErrorView
import com.priorDev.pokerroutejc.presentation.core.LoaderIndicatorView
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.presentation.pokemonDetails.PokemonDetailsEvents
import com.priorDev.pokerroutejc.presentation.typeDetails.TypeGroup
import com.priorDev.pokerroutejc.ui.theme.PokemonRRouteJCTheme

@Composable
fun DamageRelationView(
    states: DamageRelationStates,
    modifier: Modifier = Modifier,
    onEvents: (PokemonDetailsEvents) -> Unit
) {
    val damageKeys = states.damageRelations.keys.toList()

    Box(modifier = modifier) {
        LazyColumn {
            items(damageKeys) { key ->
                val damageRelation = states.damageRelations[key]
                    ?.map { it.type }
                    .orEmpty()
                if (damageRelation.isEmpty()) return@items

                TypeGroup(
                    title = key.asString(),
                    damageRelation = damageRelation,
                    painter = painterResource(id = R.drawable.icon_defensive)
                )
            }
        }
        LoaderIndicatorView(states.loading)
        ErrorView(states.errorState, onEvents)
    }
}

@PreviewLightDark
@Composable
fun DamageRelationLight() {
    PokemonRRouteJCTheme {
        DamageRelationView(
            states = DamageRelationStates(
                damageRelations = mapOf(
                    UiMessages.DynamicMessage("DoubleDamage") to listOf(
                        DamageValue(2f, TypeData(1, "Flying")),
                        DamageValue(2f, TypeData(1, "Fire")),
                        DamageValue(2f, TypeData(1, "Ice")),
                        DamageValue(2f, TypeData(1, "Psychic")),
                        DamageValue(2f, TypeData(1, "Electric"))
                    )
                )
            ),
            onEvents = {}
        )
    }
}
