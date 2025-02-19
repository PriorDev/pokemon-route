package com.priorDev.pokerroutejc.di

import com.priorDev.pokerroutejc.data.network.pkType.TypeNetService
import com.priorDev.pokerroutejc.data.TypeRepoImp
import com.priorDev.pokerroutejc.data.network.pkType.TypeNetServiceImp
import com.priorDev.pokerroutejc.data.TypeRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class TypeRepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun providesTypeRepository(typeRepositoryImp: TypeRepoImp): TypeRepo

    @Binds
    @ViewModelScoped
    abstract fun providesTypeService(service: TypeNetServiceImp): TypeNetService
}
