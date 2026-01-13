package com.priorDev.pokerroutejc.core.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.priorDev.pokerroutejc.navigation.Routes
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
