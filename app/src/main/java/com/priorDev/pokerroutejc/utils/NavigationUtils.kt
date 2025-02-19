package com.priorDev.pokerroutejc.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlinx.serialization.descriptors.SerialDescriptor

fun SerialDescriptor.toRoute(): String {
    return this.toString()
        .replace("(", "")
        .replace(")", "")
}

fun <T: Routes>NavController.navigateToTab(routeObj: T) {
    navigate(routeObj) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
