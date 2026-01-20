package com.priorDev.pokerroutejc.presentation.typeDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.core.getTypeColor
import com.priorDev.pokerroutejc.domain.types.models.DamageRelationsData
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.domain.types.models.DamageRelation
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.MyTopBar
import com.priorDev.pokerroutejc.presentation.core.ScreenTemplate
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.ui.theme.PokemonRRouteJCTheme

@Composable
fun DetailsTypeScreen(
    states: DetailsTypeState,
    onEvents: (DetailsTypeEvents) -> Unit
) {
    val details = states.details

    ScreenTemplate(
        loadingIndicator = states.loadingIndicator,
        errorState = states.errorState,
        onEvent = onEvents,
        topBar = {
            MyTopBar(
                title = UiMessages.DynamicMessage(details.name.uppercase()),
            )
        }
    ) {
        LazyColumn(
            Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                TypeGroup(
                    title = stringResource(id = R.string.damageX2),
                    damageRelation = details.damageRelationsData.doubleDamageTo,
                    painter = painterResource(id = R.drawable.icon_attack)
                )
            }

            item {
                TypeGroup(
                    title = stringResource(id = R.string.half_damage),
                    damageRelation = details.damageRelationsData.halfDamageTo,
                    painter = painterResource(id = R.drawable.icon_attack)
                )
            }

            item {
                TypeGroup(
                    title = stringResource(id = R.string.no_damage),
                    damageRelation = details.damageRelationsData.noDamageTo,
                    painter = painterResource(id = R.drawable.icon_attack)
                )
            }

            item {
                TypeGroup(
                    title = stringResource(id = R.string.damageX2),
                    damageRelation = details.damageRelationsData.doubleDamageFrom,
                    painter = painterResource(id = R.drawable.icon_defensive)
                )
            }

            item {
                TypeGroup(
                    title = stringResource(id = R.string.half_damage),
                    damageRelation = details.damageRelationsData.halfDamageFrom,
                    painter = painterResource(id = R.drawable.icon_defensive)
                )
            }

            item {
                TypeGroup(
                    title = stringResource(id = R.string.no_damage),
                    damageRelation = details.damageRelationsData.noDamageFrom,
                    painter = painterResource(id = R.drawable.icon_defensive)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun DetailsTypeScreenPreview() {
    val typesList = listOf(
        TypeData(id = 1, name = "Fuego"),
        TypeData(1, "Hielo")
    )

    val details = TypeDetailsData(
        id = 2,
        name = "Fire",
        damageRelationsData = DamageRelationsData(
            doubleDamageFrom = typesList,
            doubleDamageTo = typesList,
            halfDamageFrom = typesList,
            halfDamageTo = typesList,
            noDamageFrom = typesList,
            x1_4DamageFrom = typesList,
            x4DamageTo = typesList
        ),
        damageRelations = DamageRelation()
    )

    PokemonRRouteJCTheme {
        DetailsTypeScreen(
            states = DetailsTypeState(
                loadingIndicator = LoadingIndicator.None,
                details = details
            ),
            onEvents = { }
        )
    }
}
