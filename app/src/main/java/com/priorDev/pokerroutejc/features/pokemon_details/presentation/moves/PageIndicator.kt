package com.priorDev.pokerroutejc.features.pokemon_details.presentation.moves

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun PageIndicator(
    pageCount: Int,
    pagerState: PagerState,
) {
    val scope = rememberCoroutineScope()
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(pageCount) { tab ->
            val color = if (pagerState.currentPage == tab) {
                MaterialTheme.colorScheme.inversePrimary
            } else {
                Color.LightGray
            }

            Canvas(
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(tab)
                        }
                    }
                    .size(20.dp)
            ) {
                drawCircle(color = color)
            }
        }
    }
}
