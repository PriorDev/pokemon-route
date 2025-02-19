package com.priorDev.pokerroutejc.featurePokemon.presentation.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.priorDev.pokerroutejc.ui.theme.PokemonRRouteJCTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PkSearchView(
//    state: PkSearchState,
//    onEvent: (PkSearchEvent) -> Unit,
) {
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        inputField = {
            SearchBarDefaults.InputField(
                query = "text",
                onQueryChange = { },
                onSearch = { },
                expanded = false,
                onExpandedChange = { },
                placeholder = { Text("Hinted search text") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                enabled = false
            )
        },
        expanded = false,
        onExpandedChange = { }
    ) { }
}

@Composable
@Preview
private fun PkSearchViewPreview() {
    PokemonRRouteJCTheme {
        PkSearchView(
//            state = PkSearchState(""),
//            onEvent = {}
        )
    }
}
