package com.priorDev.pokerroutejc.presentation.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.priorDev.pokerroutejc.ui.Routes
import kotlinx.serialization.descriptors.SerialDescriptor

fun SerialDescriptor.toRoute(): String {
    return this.toString()
        .replace("(", "")
        .replace(")", "")
}

fun NavController.navigateToTab(routeObj: Routes) {
    navigate(routeObj) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
