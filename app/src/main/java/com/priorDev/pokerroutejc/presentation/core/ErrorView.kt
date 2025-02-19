package com.priorDev.pokerroutejc.presentation.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate

@Composable
fun ErrorView(
    errorState: ErrorState?
) {
    errorState?.let {
        when (errorState.displayAs) {
            DisplayError.Dialog -> {
                ErrorDialog(errorState)
            }

            DisplayError.FullScreen -> {
                FullScreenError(errorState)
            }
        }
    }
}

@Composable
private fun ErrorDialog(
    errorState: ErrorState
) {
    CustomAlertDialog(
        AlertDialogModel(
            isVisible = true,
            title = errorState.networkError.errorTitle,
            message = when (errorState.networkError) {
                is NetworkError.ClientError -> {
                    UiMessages.DynamicMessage(errorState.networkError.serverMessage.orEmpty())
                }

                is NetworkError.ServerError -> {
                    UiMessages.DynamicMessage(errorState.networkError.serverMessage)
                }

                else -> {
                    UiMessages.DynamicMessage("")
                }
            },
            confirmText = errorState.actionButtonText.takeIf { errorState.isActionButtonVisible },
            onConfirm = {
                errorState.onAction.invoke()
            },
            dismissText = errorState.dismissButtonText.takeIf { errorState.isDismissButtonVisible },
            onDismiss = {
                errorState.onDismiss.invoke()
            }
        )
    )
}

@Composable
private fun FullScreenError(
    errorState: ErrorState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.error_icon),
                contentDescription = stringResource(R.string.error_icon),
                modifier = Modifier.size(200.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = errorState.networkError.errorTitle.asString(),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            HorizontalDivider(Modifier.padding(vertical = 16.dp))

            when (errorState.networkError) {
                is NetworkError.ClientError -> {
                    Text(
                        text = errorState.networkError.serverMessage.orEmpty(),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }

                is NetworkError.ServerError -> {
                    Text(
                        text = errorState.networkError.serverMessage,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }

                else -> {
                    // Don't display anything
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (errorState.isActionButtonVisible) {
                    OutlinedButton(
                        colors = ButtonDefaults.buttonColors(),
                        onClick = {
                            errorState.onAction.invoke()
                        }
                    ) {
                        Text(text = errorState.actionButtonText.asString())
                    }
                }

                Spacer(Modifier.width(16.dp))

                if (errorState.isDismissButtonVisible) {
                    OutlinedButton(
                        colors = ButtonDefaults.filledTonalButtonColors(),
                        onClick = {
                            errorState.onDismiss.invoke()
                        }
                    ) {
                        Text(text = errorState.dismissButtonText.asString())
                    }
                }
            }
        }
    }
}

class NetworkErrorProvider : PreviewParameterProvider<ErrorState> {
    override val values: Sequence<ErrorState>
        get() = sequenceOf(
            ErrorState(
                displayAs = DisplayError.Dialog,
                networkError = NetworkError.ClientError("Client error"),
                actionButtonText = UiMessages.DynamicMessage("Retry"),
                isActionButtonVisible = true,
                dismissButtonText = UiMessages.DynamicMessage("Dismiss"),
                isDismissButtonVisible = true
            ),

            ErrorState(
                displayAs = DisplayError.Dialog,
                networkError = NetworkError.ServerError("Server down"),
                actionButtonText = UiMessages.DynamicMessage("Retry"),
                isActionButtonVisible = true,
            ),

            ErrorState(
                displayAs = DisplayError.FullScreen,
                networkError = NetworkError.UnknownError,
            ),

            ErrorState(
                displayAs = DisplayError.FullScreen,
                networkError = NetworkError.UnableToConnect,
            ),

            ErrorState(
                displayAs = DisplayError.FullScreen,
                networkError = NetworkError.EmptyContent,
            ),
        )
}

@Composable
@Preview
private fun ErrorViewPreview(
    @PreviewParameter(NetworkErrorProvider::class) networkError: ErrorState
) {
    PreviewTemplate {
        ErrorView(
            errorState = networkError
        )
    }
}
