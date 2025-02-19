package com.priorDev.pokerroutejc.presentation.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.data.network.NetworkError
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate

@Composable
fun ErrorView(
    networkError: NetworkError
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
                text = networkError.userFriendlyMessage.asString(),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            HorizontalDivider(Modifier.padding(vertical = 16.dp))

            when (networkError) {
                is NetworkError.ClientError -> {
                    Text(
                        text = networkError.serverMessage.orEmpty(),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }

                is NetworkError.ServerError -> {
                    Text(
                        text = networkError.serverMessage,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }

                else -> {
                    // Don't display anything
                }
            }
        }
    }
}

@Composable
@Preview
private fun ErrorViewPreview() {
    PreviewTemplate {
        ErrorView(
            networkError = NetworkError.ServerError("Server down")
        )
    }
}