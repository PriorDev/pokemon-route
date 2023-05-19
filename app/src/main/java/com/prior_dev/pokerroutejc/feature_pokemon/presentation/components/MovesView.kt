package com.prior_dev.pokerroutejc.feature_pokemon.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prior_dev.pokerroutejc.R
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.details.PokemonDetailsViewModel
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.utils.MoveViewStates

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovesView() {
    val viewModel: PokemonDetailsViewModel = hiltViewModel()
    val cardPadding  = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
    val moveStates by viewModel.moveStates.observeAsState(MoveViewStates())

    Column(Modifier.fillMaxSize()) {
        if(moveStates.isLoading){
            LinearProgressIndicator(Modifier.fillMaxWidth())
        }
        LazyColumn() {
            item{
                Box(modifier = Modifier
                    .fillMaxWidth()
                ){
                    Text(
                        text = stringResource(id = R.string.moves),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(MaterialTheme.colors.background)
                            .fillMaxWidth(.6f)
                    )

                    androidx.compose.animation.AnimatedVisibility(
                        visible = !moveStates.isLoading,
                        enter = slideInHorizontally(
                            initialOffsetX = { it }
                        ),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    ) {
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(0.dp)
                                .background(MaterialTheme.colors.primary),
                            onClick = viewModel::onToggleFilterVisibility
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.filters),
                            )
                        }
                    }
                }
            }
            item {
                AnimatedVisibility(visible = moveStates.isFiltersExpanded) {
                    Filters()
                }
            }
            items(viewModel.moves){ move ->
                if(move.isVisible){
                    ItemMove(
                        move = move,
                        modifier = Modifier
                            .padding(cardPadding)
                            .animateItemPlacement()
                    )
                }
            }
            item{
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}
