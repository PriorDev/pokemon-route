package com.priorDev.pokerroutejc.presentation.typeDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.presentation.reusable.ItemType
import com.priorDev.pokerroutejc.ui.theme.PokemonRRouteJCTheme

@Composable
fun TypeGroup(
    title: String?,
    painter: Painter?,
    damageRelation: List<TypeData>
) {
    if (damageRelation.isEmpty()) return
    val rowCount by remember {
        derivedStateOf { ((damageRelation.size +1) / 2) }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            painter?.let {
                Icon(
                    modifier = Modifier
                        .size(50.dp),
                    painter = painter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            title?.let {
                Text(
                    modifier = Modifier,
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color =  MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        for (i in 0 until rowCount) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        when {
                            rowCount == 1 -> MaterialTheme.shapes.extraLarge
                            i == 0 -> MaterialTheme.shapes.extraLarge.copy(
                                bottomStart = CornerSize(0.dp),
                                bottomEnd = CornerSize(0.dp)
                            )
                            i == rowCount - 1 -> MaterialTheme.shapes.extraLarge.copy(
                                topStart = CornerSize(0.dp),
                                topEnd = CornerSize(0.dp)
                            )
                            else -> MaterialTheme.shapes.small
                        }
                    )
            ) {
                ItemType(
                    modifier = Modifier
                        .weight(1f)
                        .height(64.dp),
                    type = damageRelation[i * 2]
                )

                val index2 = (i * 2) + 1
                if (index2 < damageRelation.size) {
                    Spacer(modifier = Modifier.width(2.dp))
                    ItemType(
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp),
                        type = damageRelation[index2]
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@PreviewLightDark
@Composable
private fun TypeGroupPreview() {
    PokemonRRouteJCTheme {
        TypeGroup(
            title = "Double damage to",
            painter = painterResource(id = R.drawable.icon_defensive),
            damageRelation = listOf(
                TypeData(1, "Hielo"),
                TypeData(1, "Fiego"),
                TypeData(1, "Fantasma"),
                TypeData(1, "Bicho"),
                TypeData(1, "Siniestro")
            )
        )
    }
}
