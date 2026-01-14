package com.priorDev.pokerroutejc.presentation.typeDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.core.getTypeColor
import com.priorDev.pokerroutejc.domain.types.models.DamageRelationsData
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData
import com.priorDev.pokerroutejc.ui.theme.Defensive
import com.priorDev.pokerroutejc.ui.theme.DoubleDamageColor
import com.priorDev.pokerroutejc.ui.theme.HalfDamageColor
import com.priorDev.pokerroutejc.ui.theme.NoDamageColor
import com.priorDev.pokerroutejc.ui.theme.Offensive
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
    val colorType = details.id.getTypeColor()

    ScreenTemplate(
        loadingIndicator = states.loadingIndicator,
        errorState = states.errorState,
        onEvent = onEvents,
        topBar = {
            MyTopBar(
                title = UiMessages.DynamicMessage(details.name.uppercase()),
                color = colorType
            )
        }
    ) {
        LazyColumn(
            Modifier
                .background(colorType)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Card(
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Offensive),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp),
                            painter = painterResource(id = R.drawable.icon_attack),
                            contentDescription = stringResource(id = R.string.offensive),
                            tint = Color.Black,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        ItemDamageRelation(
                            title = stringResource(id = R.string.damageX2),
                            background = DoubleDamageColor,
                            list = details.damageRelationsData.doubleDamageTo,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ItemDamageRelation(
                            title = stringResource(id = R.string.half_damage),
                            background = HalfDamageColor,
                            list = details.damageRelationsData.halfDamageTo
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ItemDamageRelation(
                            title = stringResource(id = R.string.no_damage),
                            background = NoDamageColor,
                            list = details.damageRelationsData.noDamageTo
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Card(
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Defensive),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(50.dp),
                            painter = painterResource(id = R.drawable.icon_defensive),
                            contentDescription = stringResource(id = R.string.defensive),
                            tint = Color.Black,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        ItemDamageRelation(
                            title = stringResource(id = R.string.damageX2),
                            background = DoubleDamageColor,
                            list = details.damageRelationsData.doubleDamageFrom,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ItemDamageRelation(
                            title = stringResource(id = R.string.half_damage),
                            background = HalfDamageColor,
                            list = details.damageRelationsData.halfDamageFrom,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ItemDamageRelation(
                            title = stringResource(id = R.string.no_damage),
                            background = NoDamageColor,
                            list = details.damageRelationsData.noDamageFrom,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
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
