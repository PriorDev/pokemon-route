package com.prior_dev.pokerroutejc.feature_types.di

import com.prior_dev.pokerroutejc.feature_types.data.TypeRepositoryImp
import com.prior_dev.pokerroutejc.feature_types.domain.TypeRepository
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
}