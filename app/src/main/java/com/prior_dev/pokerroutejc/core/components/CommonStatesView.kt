package com.prior_dev.pokerroutejc.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.prior_dev.pokerroutejc.core.CommonStates

@Composable
fun CommonStatesView(
    onDismiss: () -> Unit,
    states: CommonStates,
){
    if(states.isLoading){
        Box(
            Modifier.fillMaxSize()
        ){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    DisposableMessage(message = states.message, onDismiss = onDismiss)
}