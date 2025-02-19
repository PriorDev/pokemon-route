package com.priorDev.pokerroutejc.featurePokemon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.priorDev.pokerroutejc.presentation.reusable.PreviewTemplate
import com.priorDev.pokerroutejc.featurePokemon.domain.AbilityDetailsData

@Composable
fun AbilityDialog(
    ability: AbilityDetailsData,
    onDismiss: () -> Unit,
    isLoading: Boolean
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colors.background
                )
                .padding(16.dp)
        ) {
            Column {
                if (!isLoading) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = ability.name,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6
                    )
                    Divider()
                    Text(
                        text = ability.shortEffect,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = ability.effect)
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Suppress("MaxLineLength")
@Composable
@Preview(showSystemUi = true)
private fun AbilityDialogPreview() {
    PreviewTemplate {
        AbilityDialog(
            ability = AbilityDetailsData(
                name = "Ability name",
                shortEffect = "This is the Description of a short effect ability",
                effect = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
            ),
            onDismiss = { },
            isLoading = false
        )
    }
}
