package com.priorDev.pokerroutejc.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ScreenTemplate(
    modifier: Modifier = Modifier,
    errorState: ErrorState,
    dialogModel: AlertDialogModel,
    loadingIndicator: LoadingIndicator,
    topBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        modifier = modifier
    ) { innerPadding ->
        ScreenTemplate(
            loadingIndicator = loadingIndicator,
            errorState = errorState,
            dialogModel = dialogModel,
            content = content,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenTemplate(
    modifier: Modifier = Modifier,
    loadingIndicator: LoadingIndicator,
    errorState: ErrorState,
    dialogModel: AlertDialogModel,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = loadingIndicator is LoadingIndicator.Refreshing,
        onRefresh = onRefresh
    )

    Box {
        ScreenTemplate(
            loadingIndicator = loadingIndicator,
            errorState = errorState,
            dialogModel = dialogModel,
            content = content,
            modifier = modifier.pullRefresh(pullRefreshState)
        )

        PullRefreshIndicator(
            refreshing = loadingIndicator is LoadingIndicator.Refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun ScreenTemplate(
    modifier: Modifier = Modifier,
    loadingIndicator: LoadingIndicator,
    errorState: ErrorState,
    dialogModel: AlertDialogModel,
    content: @Composable () -> Unit
) {
    Box(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CustomAlertDialog(dialogModel)

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

        ErrorView(errorState)
    }
}
