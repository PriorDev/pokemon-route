package com.priorDev.pokerroutejc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application()

// TODO: Add cache for pokemon types
// TODO: Add error page to Pokemon types list screen
// TODO: Add new error type strategy to apollo calls
// TODO: Migrate to ktor
// TODO: Migrate to koin