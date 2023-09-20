package com.prior_dev.pokerroutejc.feature_types.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 16.dp),
        colors = CardDefaults.cardColors(containerColor = background),
    ) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
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

@Preview(showSystemUi = true)
@Composable
fun ItemDamageRelationPreview(){
    ItemDamageRelation(
        title = "Double damage to",
        background = MaterialTheme.colorScheme.tertiary,
        list = listOf(
            TypeData(1, "Hielo"),
            TypeData(1, "Fiego"),
            TypeData(1, "Fantasma"),
            TypeData(1, "Bicho"),
        )
    )
}