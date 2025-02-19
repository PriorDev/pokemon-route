package com.priorDev.pokerroutejc.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.priorDev.pokerroutejc.data.database.MyDataBase
import com.priorDev.pokerroutejc.data.network.EndPoints
import com.priorDev.pokerroutejc.data.network.INetWorkService
import com.priorDev.pokerroutejc.data.network.INetworkCaller
import com.priorDev.pokerroutejc.data.network.KtorNetworkServiceImp
import com.priorDev.pokerroutejc.data.network.MakeKtorNetworkCall
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
import io.ktor.serialization.kotlinx.json.DefaultJson
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
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
            .baseUrl(EndPoints.BASE_URL)
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
            .serverUrl(EndPoints.QL_BASE_URL)
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
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    @Provides
    @Singleton
    fun provideNetworkService(
        client: HttpClient,
        caller: MakeKtorNetworkCall
    ): INetWorkService {
        return KtorNetworkServiceImp(
            client = client,
            makeKtorNetworkCall = caller
        )
    }

    @Provides
    @Singleton
    fun provideNetworkCaller(
        dispatcher: CoroutineDispatcher
    ): INetworkCaller {
        return MakeKtorNetworkCall(dispatcher)
    }
}
