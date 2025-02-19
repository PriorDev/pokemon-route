package com.priorDev.pokerroutejc

import android.app.Application
import com.priorDev.pokerroutejc.di.kAppModule
import com.priorDev.pokerroutejc.di.kPokemonModule
import com.priorDev.pokerroutejc.di.kTypeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                kAppModule,
                kTypeModule,
                kPokemonModule
            )
        }
    }
}

// TODO Modify NavBottomItems
// TODO Add cache for pokemon types
// TODO Add new error type strategy to apollo calls
// TODO Migrate to ktor
// TODO Migrate to koin
// TODO Add Top App Bar Action
// TODO Add a Bottom sheet
