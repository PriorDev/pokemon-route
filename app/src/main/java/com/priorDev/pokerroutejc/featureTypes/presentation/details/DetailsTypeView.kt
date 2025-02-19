package com.priorDev.pokerroutejc.featureTypes.presentation.details

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.presentation.reusable.CommonStatesView
import com.priorDev.pokerroutejc.core.getTypeColor
import com.priorDev.pokerroutejc.featureTypes.domain.DamageRelationsData
import com.priorDev.pokerroutejc.featureTypes.domain.TypeData
import com.priorDev.pokerroutejc.featureTypes.domain.TypeDetailsData
import com.priorDev.pokerroutejc.featureTypes.presentation.components.ItemDamageRelation
import com.priorDev.pokerroutejc.ui.theme.Defensive
import com.priorDev.pokerroutejc.ui.theme.DoubleDamageColor
import com.priorDev.pokerroutejc.ui.theme.HalfDamageColor
import com.priorDev.pokerroutejc.ui.theme.NoDamageColor
import com.priorDev.pokerroutejc.ui.theme.Offensive
import com.priorDev.pokerroutejc.R

@Composable
fun DetailsTypeView(
    states: CommonStates,
    details: TypeDetailsData,
    onEvents: (DetailsTypeEvents) -> Unit
) {
    val colorType = details.name.getTypeColor()

    Scaffold(
        topBar = {
            Text(
                text = details.name.uppercase(),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorType)
                    .padding(4.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
            )
        },
    ) { innerPadding ->
        CommonStatesView(
            onDismiss = { onEvents(DetailsTypeEvents.onDismiss) },
            commonStates = states
        )

        if (states.isLoading) return@Scaffold

        LazyColumn(
            Modifier
                .padding(innerPadding)
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun DetailsTypePreview() {
    val typesList = listOf(
        TypeData(id = 1, name = "Fuego"),
        TypeData(1, "Hielo")
    )

    val states = CommonStates(isLoading = false)
    val details = TypeDetailsData(
        name = "Fire",
        damageRelationsData = DamageRelationsData(
            doubleDamageFrom = typesList,
            doubleDamageTo = typesList,
            halfDamageFrom = typesList,
            halfDamageTo = typesList,
            noDamageFrom = typesList,
            x1_4DamageFrom = typesList,
            x4DamageTo = typesList
        )
    )
    DetailsTypeView(
        states = states,
        details = details,
        onEvents = { }
    )
}
