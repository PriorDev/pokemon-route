package com.priorDev.pokerroutejc.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.data.network.utils.NetworkError

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
        loaderIndicator(loadingIndicator)
        ErrorView(errorState)
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenTemplatePreview() {
    ScreenTemplate(
        loadingIndicator = LoadingIndicator.SolidSpinningWheel,
        errorState = null
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "Content")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenTemplateWithTopBarAndFabPreview() {
    ScreenTemplate(
        loadingIndicator = LoadingIndicator.None,
        errorState = null,
        topBar = { MyTopBar(title = UiMessages.StringResource(R.string.app_name)) },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        }
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "Content")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenTemplateLoadingPreview() {
    ScreenTemplate(
        loadingIndicator = LoadingIndicator.SolidSpinningWheel,
        errorState = null
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "Content")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenTemplateErrorPreview() {
    ScreenTemplate(
        loadingIndicator = LoadingIndicator.None,
        errorState = ErrorState(
            displayAs = DisplayError.Dialog,
            networkError = NetworkError.UnableToConnect,
            isActionButtonVisible = true,
            isDismissButtonVisible = true,
        )
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "Content")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenTemplateFullScreenErrorPreview() {
    ScreenTemplate(
        loadingIndicator = LoadingIndicator.None,
        errorState = ErrorState(
            displayAs = DisplayError.FullScreen,
            networkError = NetworkError.UnableToConnect,
            isActionButtonVisible = true
        )
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "Content")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenTemplateWithRefreshPreview() {
    ScreenTemplate(
        loadingIndicator = LoadingIndicator.Refreshing,
        errorState = null,
        onRefresh = {}
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "Content")
        }
    }
}
