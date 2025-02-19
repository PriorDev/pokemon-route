package com.priorDev.pokerroutejc.presentation.pokemonDetails.moves

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.presentation.reusable.ItemType
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate

@Composable
fun ItemMove(
    move: MoveDetailsData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = move.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            move.type?.let {
                ItemType(
                    type = it,
                    modifier = Modifier.height(32.dp),
                    elevation = 0.dp
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.Damage, move.damageClass),
                    modifier = Modifier.padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center
                )

                if(move.machineNumber.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.machine, move.machineNumber),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }

                if(move.level > 0) {
                    Text(
                        text = stringResource(id = R.string.learned_at, move.level),
                        modifier = Modifier.padding(start = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

//            Text(text = move.generationName.uppercase())
//            Divider()
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = stringResource(id = R.string.power) + " ${move.power}")
//
//                Text(text = stringResource(id = R.string.accuracy) + " ${move.accuracy}")
//            }
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(text = stringResource(id = R.string.pp) + " ${move.pp}")
//                Text(text = stringResource(id = R.string.priority) + " ${move.priority}")
//            }
//
//            Divider()
//
//            SelectionContainer {
//                Text(text = move.effect, modifier = Modifier.fillMaxWidth(.98f))
//            }

//            LazyRow(
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier
//                    .padding(8.dp)
//            ) {
//                val versions = move.versionGroupDetails.distinctBy { it.moveLearnMethodId }
//                items(versions) { version ->
//                    Card(elevation = 4.dp) {
//                        Column(
//                            Modifier
//                                .fillMaxWidth()
//                                .padding(4.dp)
//                        ) {
//                            Text(
//                                text = stringResource(id = R.string.learned_at)
//                                    .plus(version.levelLearnedAt)
//                                    .uppercase()
//                            )
//                            Text(
//                                text = stringResource(id = R.string.method)
//                                    .plus(version.moveLearnMethodName)
//                                    .uppercase()
//                            )
//                        }
//                    }
//                }
//            }
    }
}

@Suppress("MaxLineLength")
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ItemMovePreview() {
    val move = MoveDetailsData(
        name = "Nombre",
        accuracy = 5,
        power = 5,
        pp = 5,
        priority = 5,
        type = TypeData(1, "fire"),
        damageClass = "Fisico",
        generationName = "Gold",
        effect = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        learnMethod = "Learn Method",
        machineNumber = "",
        level = 3
    )
    
    PreviewTemplate {
        ItemMove(move = move)
    }
}
