package com.priorDev.pokerroutejc.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <E> ScreenTemplate(
    modifier: Modifier = Modifier,
    errorState: ErrorState<E>?,
    loadingIndicator: LoadingIndicator,
    onEvent: (E) -> Unit,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        floatingActionButton = floatingActionButton,
        modifier = modifier,
    ) { innerPadding ->
        ScreenTemplate(
            loadingIndicator = loadingIndicator,
            errorState = errorState,
            onEvent = onEvent,
            content = content,
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <E> ScreenTemplate(
    modifier: Modifier = Modifier,
    loadingIndicator: LoadingIndicator,
    errorState: ErrorState<E>?,
    onEvent: (E) -> Unit,
    onRefresh: () -> Unit,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        floatingActionButton = floatingActionButton,
        modifier = modifier,
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = loadingIndicator is LoadingIndicator.Refreshing,
            onRefresh = onRefresh,
            modifier = modifier
        ) {
            ScreenTemplate(
                loadingIndicator = loadingIndicator,
                errorState = errorState,
                onEvent = onEvent,
                content = content,
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
            )
        }
    }

}

@Composable
fun <E> ScreenTemplate(
    modifier: Modifier = Modifier,
    loadingIndicator: LoadingIndicator,
    errorState: ErrorState<E>?,
    onEvent: (E) -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        content()

        when (loadingIndicator) {
            LoadingIndicator.None,

            LoadingIndicator.Refreshing -> {
                // Don't display anything
            }
            LoadingIndicator.SolidSpinningWheel -> {
                Box(
                    modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            LoadingIndicator.TopLinear -> {
                LinearProgressIndicator(
                    Modifier.fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
            }
        }

        ErrorView(errorState, onEvent)
    }
}
