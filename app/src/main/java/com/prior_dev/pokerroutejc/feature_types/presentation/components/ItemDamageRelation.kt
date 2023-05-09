package com.prior_dev.pokerroutejc.feature_types.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prior_dev.pokerroutejc.core.components.ItemType
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData

@Composable
fun ItemDamageRelation(
    modifier: Modifier = Modifier,
    title: String,
    background: Color,
    list: List<TypeData>,
) {
    if(list.isEmpty())
        return

    Card(
        modifier = modifier,
        elevation = 16.dp,
        backgroundColor = background,
    ) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(Modifier.padding(horizontal = 4.dp)){
                items(list){ type ->
                    ItemType(
                        modifier = Modifier
                            .height(50.dp)
                            .width(100.dp)
                            .padding(horizontal = 8.dp),
                        type = type
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}