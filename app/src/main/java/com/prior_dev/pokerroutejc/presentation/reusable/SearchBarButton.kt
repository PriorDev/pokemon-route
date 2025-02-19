package com.prior_dev.pokerroutejc.presentation.reusable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prior_dev.pokerroutejc.R
import com.prior_dev.pokerroutejc.ui.theme.PokemonRRouteJCTheme

@Composable
fun SearchBarButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .height(56.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.extraLarge
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            modifier = Modifier
                .padding(end = 16.dp),
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search)
        )
    }
}


@Preview
@Composable
fun SearchBarButtonPreview(modifier: Modifier = Modifier) {
    PokemonRRouteJCTheme {
        SearchBarButton(
            modifier = modifier.fillMaxWidth(),
            text = "Search...",
            onClick = { }
        )
    }
}