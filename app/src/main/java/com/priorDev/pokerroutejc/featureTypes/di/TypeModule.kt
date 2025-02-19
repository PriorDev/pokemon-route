package com.priorDev.pokerroutejc.featureTypes.di

import com.priorDev.pokerroutejc.data.database.MyDataBase
import com.priorDev.pokerroutejc.featureTypes.data.network.TypeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object TypeModule {
    @ViewModelScoped
    @Provides
    fun providerTypeApi(retrofit: Retrofit): TypeApi =
        retrofit.create(TypeApi::class.java)

    @ViewModelScoped
    @Provides
    fun providerTypeDao(db: MyDataBase) = db.typeDao
}
