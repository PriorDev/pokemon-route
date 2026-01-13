package com.priorDev.pokerroutejc.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.priorDev.pokerroutejc.core.data.SettingsRepo
import com.priorDev.pokerroutejc.core.data.SettingsRepoImp
import com.priorDev.pokerroutejc.core.data.dataStore.DataStoreManager
import com.priorDev.pokerroutejc.core.data.dataStore.DataStoreManagerImp
import com.priorDev.pokerroutejc.core.data.dataStore.pkDataStore
import com.priorDev.pokerroutejc.core.data.database.MyDataBase
import com.priorDev.pokerroutejc.core.data.network.ApolloCaller
import com.priorDev.pokerroutejc.core.data.network.ApolloCallerImp
import com.priorDev.pokerroutejc.core.data.network.NetworkCaller
import com.priorDev.pokerroutejc.core.data.network.NetworkCallerImp
import com.priorDev.pokerroutejc.core.data.network.NetworkService
import com.priorDev.pokerroutejc.core.data.network.NetworkServiceKtor
import com.priorDev.pokerroutejc.core.data.network.utils.EndPoints
import com.priorDev.pokerroutejc.core.utils.GlobalEventChannelImp
import com.priorDev.pokerroutejc.core.utils.GlobalEventChannel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val kAppModule = module {
    single {
        HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("KTOR_LOG", message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    single {
        Room.databaseBuilder(
            context = get(),
            klass = MyDataBase::class.java,
            name = MyDataBase.DATABASE_NAME
        ).build()
    }

    single {
        Dispatchers.IO
    }

    single<NetworkCaller> {
        NetworkCallerImp()
    }

    single<NetworkService> {
        NetworkServiceKtor(
            client = get(),
            caller = get()
        )
    }

    single<GlobalEventChannel> {
        GlobalEventChannelImp()
    }

    single {
        ApolloClient.Builder()
            .serverUrl(EndPoints.QL_BASE_URL)
            .build()
    }

    single<ApolloCaller> {
        ApolloCallerImp(
            apolloClient = get()
        )
    }

    single {
        val context = get<Context>()
        context.pkDataStore
    }

    single<DataStoreManager> {
        DataStoreManagerImp(dataStore = get())
    }

    single<SettingsRepo> {
        SettingsRepoImp(
            dataStore = get()
        )
    }
}
