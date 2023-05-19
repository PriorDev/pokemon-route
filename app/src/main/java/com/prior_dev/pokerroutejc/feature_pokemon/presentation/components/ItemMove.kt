package com.prior_dev.pokerroutejc.feature_pokemon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prior_dev.pokerroutejc.R
import com.prior_dev.pokerroutejc.core.EnumColorTypes
import com.prior_dev.pokerroutejc.core.components.ItemType
import com.prior_dev.pokerroutejc.feature_pokemon.domain.MoveDetailsData
import com.prior_dev.pokerroutejc.feature_pokemon.domain.VersionGroupDetailData
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData
import com.prior_dev.pokerroutejc.feature_types.domain.getColor

@Composable
fun ItemMove(
    modifier: Modifier = Modifier,
    move: MoveDetailsData,
) {
    val backgroundColor = move.type?.getColor() ?: EnumColorTypes.Normal.color
    Card(
        modifier = modifier,
        backgroundColor = backgroundColor,
        elevation = 4.dp
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = move.name.uppercase(),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
            )
            Divider()
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.power) + " ${move.power}")

                Text(text = stringResource(id = R.string.accuracy) + " ${move.accuracy}")
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.pp) + " ${move.pp}")
                Text(text = stringResource(id = R.string.priority) + " ${move.priority}")
            }

            move.type?.let {
                ItemType(type = it, modifier = Modifier.padding(4.dp))
            }

            Text(
                text = stringResource(id = R.string.damage_type) + " ${move.damageName}",
                fontWeight = FontWeight.Bold
            )

            Text(text = move.generationName.uppercase())

            Divider()

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(8.dp)
            ){
                items(move.versionGroupDetails){ version ->
                    Card(elevation = 4.dp){
                        Column(Modifier.fillMaxWidth().padding(4.dp)) {
                            Text(
                                text = stringResource(id = R.string.learned_at) +
                                        " ${version.levelLearnedAt}".uppercase()
                            )
                            Text(text = stringResource(id = R.string.method) +
                                    " ${version.moveLearnMethodName}".uppercase()
                            )
                            Text(text = stringResource(id = R.string.group) +
                                    " ${version.versionGroupName}".uppercase()
                            )
                        }
                    }
                }
            }

        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ItemMovePreview(){
    val move = MoveDetailsData(
        isVisible = true,
        name = "Nombre",
        id = "0".toBigInteger(),
        versionGroupDetails = listOf(
            VersionGroupDetailData(
                levelLearnedAt = 1,
                moveLearnMethodId = "0".toBigInteger(),
                moveLearnMethodName = "Subir de nivel",
                versionGroupId = "0".toBigInteger(),
                versionGroupName = "Version Gold"
            ),
            VersionGroupDetailData(
                levelLearnedAt = 1,
                moveLearnMethodId = "0".toBigInteger(),
                moveLearnMethodName = "Subir de nivel",
                versionGroupId = "0".toBigInteger(),
                versionGroupName = "Version Gold"
            ),
        ),
        accuracy = 5,
        power = 5,
        pp = 5,
        priority = 5,
        type = TypeData(1, "fire"),
        damageName = "Fisico",
        generationName = "Gold",
        pastValues = emptyList()
    )

    ItemMove(move = move)
}

