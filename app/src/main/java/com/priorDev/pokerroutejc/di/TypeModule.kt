package com.priorDev.pokerroutejc.di

import com.priorDev.pokerroutejc.data.database.MyDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TypeModule {
    @ViewModelScoped
    @Provides
    fun providerTypeDao(db: MyDataBase) = db.typeDao
}
