package com.priorDev.pokerroutejc.core.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Deprecated(
    message = "PullToRefreshBox is deprecated, use the M3 implementation directly or ScreenTemplate",
    replaceWith = ReplaceWith("ScreenTemplate")
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshBox(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    androidx.compose.material3.pulltorefresh.PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        content()
    }
}
