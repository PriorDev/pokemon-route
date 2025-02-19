package com.priorDev.pokerroutejc.featureTypes.di

import com.priorDev.pokerroutejc.data.network.pkType.TypeRepositoryImp
import com.priorDev.pokerroutejc.data.network.pkType.TypeService
import com.priorDev.pokerroutejc.data.network.pkType.TypeServiceImp
import com.priorDev.pokerroutejc.featureTypes.domain.TypeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun providesTypeRepository(typeRepositoryImp: TypeRepositoryImp): TypeRepository

    @Binds
    @ViewModelScoped
    abstract fun providesTypeService(service: TypeServiceImp): TypeService
}
