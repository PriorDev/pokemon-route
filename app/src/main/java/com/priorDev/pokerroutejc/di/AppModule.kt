package com.priorDev.pokerroutejc.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.priorDev.pokerroutejc.data.database.MyDataBase
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.utils.IGlobalEventChannel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providerRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MyDataBase::class.java, MyDataBase.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun providesIoDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun providesGlobalEventChannel(): IGlobalEventChannel = GlobalEventChannel

    @Provides
    @Singleton
    fun providesApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
            .build()
    }

    @Provides
    @Singleton
    fun providesHttClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }
}
