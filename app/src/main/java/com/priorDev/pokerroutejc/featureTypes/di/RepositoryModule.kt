package com.priorDev.pokerroutejc.featureTypes.di

import com.priorDev.pokerroutejc.data.network.pkType.ITypeService
import com.priorDev.pokerroutejc.data.network.pkType.ITypeRepositoryImp
import com.priorDev.pokerroutejc.data.network.pkType.TypeNetworkServices
import com.priorDev.pokerroutejc.featureTypes.domain.ITypeRepository
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
    abstract fun providesTypeRepository(typeRepositoryImp: ITypeRepositoryImp): ITypeRepository

    @Binds
    @ViewModelScoped
    abstract fun providesTypeService(service: TypeNetworkServices): ITypeService
}
