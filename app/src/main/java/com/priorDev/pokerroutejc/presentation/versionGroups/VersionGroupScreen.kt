package com.priorDev.pokerroutejc.presentation.versionGroups

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.domain.pokedex.models.VersionGroupsData
import com.priorDev.pokerroutejc.presentation.core.MyTopBar
import com.priorDev.pokerroutejc.presentation.core.ScreenTemplate
import com.priorDev.pokerroutejc.presentation.core.SortOrder
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.ui.theme.PokemonRRouteJCTheme

@Composable
fun VersionGroupScreen(
    states: VersionGroupStates,
    onEvent: (VersionGroupEvent) -> Unit
) {
    ScreenTemplate(
        errorState = states.errorState,
        loadingIndicator = states.loading,
        topBar = {
            MyTopBar(
                title = UiMessages.StringResource(R.string.pokedex),
                actions = {
                    IconButton(onClick = { onEvent(VersionGroupEvent.OnToggleOrder) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Sort,
                            contentDescription = UiMessages.StringResource(R.string.sort).asString()
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(states.versionGroupList.keys.toList()) { generation ->
                states.versionGroupList[generation]?.forEach { groupVersion ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clickable {
                                onEvent(
                                    VersionGroupEvent.OnNavigate(
                                        Routes.Pokedex(groupVersion.id)
                                    )
                                )
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = generation,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(4.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Box(
                                modifier = Modifier
                                    .clip(
                                        MaterialTheme.shapes.large,
                                    )
                                    .weight(1f)
                                    .background(
                                        shape = MaterialTheme.shapes.medium,
                                        color = MaterialTheme.colorScheme.surfaceContainerLowest
                                    )
                            ) {
                                Text(
                                    text = groupVersion.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(4.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun VersionGroupScreenPreview() {
    PokemonRRouteJCTheme {
        VersionGroupScreen(
            states = VersionGroupStates(
                versionGroupList = mapOf(
                    "GEN I" to listOf(
                        VersionGroupsData(1, "Red/Blue", "GEN I"),
                        VersionGroupsData(2, "Yellow", "GEN I")
                    ),
                    "GEN II" to listOf(
                        VersionGroupsData(3, "Gold/Silver", "GEN II"),
                        VersionGroupsData(4, "Crystal", "GEN II")
                    ),
                    "GEN III" to listOf(
                        VersionGroupsData(5, "Ruby/Sapphire", "GEN III"),
                        VersionGroupsData(6, "Emerald", "GEN III"),
                        VersionGroupsData(7, "FireRed/LeafGreen", "GEN III")
                    ),
                    "GEN IV" to listOf(
                        VersionGroupsData(8, "Diamond/Pearl", "GEN IV"),
                        VersionGroupsData(9, "Platinum", "GEN IV"),
                        VersionGroupsData(10, "HeartGold/SoulSilver", "GEN IV")
                    )
                ),
                sortOrder = SortOrder.Ascending
            ),
            onEvent = {}
        )
    }
}
