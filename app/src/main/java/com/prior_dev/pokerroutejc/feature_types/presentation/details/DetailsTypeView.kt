package com.prior_dev.pokerroutejc.feature_types.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prior_dev.pokerroutejc.R
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.components.CommonStatesView
import com.prior_dev.pokerroutejc.core.getTypeColor
import com.prior_dev.pokerroutejc.feature_types.domain.TypeDetailsData
import com.prior_dev.pokerroutejc.feature_types.presentation.components.ItemDamageRelation
import com.prior_dev.pokerroutejc.ui.theme.*

@Composable
fun DetailsTypeView(
    viewModel: DetailsTypeViewModel = hiltViewModel(),
) {
    val states by viewModel.states.observeAsState(CommonStates())
    val details by viewModel.details.observeAsState(TypeDetailsData())
    val colorType = details.name.getTypeColor()
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController, colorType) {
        systemUiController.setSystemBarsColor(
            color = colorType,
            darkIcons = true
        )

        onDispose {
            systemUiController.setSystemBarsColor(
                color = Purple500,
                darkIcons = true
            )
        }
    }

    Scaffold(
        topBar = {
            Text(
                text = details.name.uppercase(),
                style = MaterialTheme.typography.h4,
                modifier = Modifier.fillMaxWidth()
                    .background(colorType)
                    .padding(4.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
            )
        },
    ) { innerPadding ->
        CommonStatesView(onDismiss = viewModel::onDismiss, states = states)
        if(states.isLoading)
            return@Scaffold

        LazyColumn(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp)
        ){
            item{
                Card(
                    elevation = 8.dp,
                    backgroundColor = Offensive,
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
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

            item{
                Spacer(modifier = Modifier.height(16.dp))
            }
            item{
                Card(
                    elevation = 8.dp,
                    backgroundColor = Defensive,
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
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
        }
    }

}