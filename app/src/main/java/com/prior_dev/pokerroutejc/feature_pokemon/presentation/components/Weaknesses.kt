package com.prior_dev.pokerroutejc.feature_pokemon.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prior_dev.pokerroutejc.R
import com.prior_dev.pokerroutejc.feature_types.domain.DamageRelationsData
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData
import java.time.format.TextStyle

@Composable
fun Weaknesses(
    modifier: Modifier = Modifier,
    weaknessesAndStrengths: DamageRelationsData
) {
    Card(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.weaknesses),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            ItemWeaknesses(
                weakness = weaknessesAndStrengths.x4DamageFrom,
                drawable = R.drawable.icon_x_4,
                drawableDescription = R.string.damagex4
            )

            ItemWeaknesses(
                weakness = weaknessesAndStrengths.doubleDamageFrom,
                drawable = R.drawable.icon_x_2,
                drawableDescription = R.string.damageX2
            )

            ItemWeaknesses(
                weakness = weaknessesAndStrengths.halfDamageFrom,
                drawable = R.drawable.icon_x_1_2,
                drawableDescription = R.string.half_damage
            )

            ItemWeaknesses(
                weakness = weaknessesAndStrengths.x1_4DamageTo,
                drawable = R.drawable.icon_x_1_4,
                drawableDescription = R.string.damage_x1_4
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview(){
    val cardPadding  = PaddingValues(horizontal = 32.dp, vertical = 16.dp)

    val damageRelationsData = DamageRelationsData(
        x4DamageFrom = listOf(
            TypeData(id = 1, name = "Rock"),
            TypeData(id = 2, name = "Water"),
        )
    )

    Weaknesses(
        Modifier
            .padding(cardPadding),
        weaknessesAndStrengths = damageRelationsData
    )
}
