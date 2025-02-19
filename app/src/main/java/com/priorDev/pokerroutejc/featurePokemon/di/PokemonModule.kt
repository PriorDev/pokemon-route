package com.priorDev.pokerroutejc.featurePokemon.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.priorDev.pokerroutejc.data.database.MyDataBase
import com.priorDev.pokerroutejc.featurePokemon.data.IPokemonNetworkService
import com.priorDev.pokerroutejc.featurePokemon.data.database.PokemonDao
import com.priorDev.pokerroutejc.featurePokemon.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.featurePokemon.data.network.PokemonApi
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonNameRemoteMediator
import com.priorDev.pokerroutejc.featurePokemon.domain.PokemonNameRemoteMediator.Companion.PAGE_SIZE
import com.priorDev.pokerroutejc.featurePokemon.domain.useCases.GetWeaknessesAndStrengths
import com.priorDev.pokerroutejc.featurePokemon.domain.useCases.PokemonUseCases
import com.priorDev.pokerroutejc.featureTypes.domain.ITypeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object PokemonModule {
    @ViewModelScoped
    @Provides
    fun providerPokemonApi(retrofit: Retrofit) = retrofit.create(PokemonApi::class.java)

    @ViewModelScoped
    @Provides
    fun providerPokemonDao(db: MyDataBase) = db.pokemonDao

    @ViewModelScoped
    @Provides
    fun providerPokemonUseCases(
        typeRepository: ITypeRepository
    ): PokemonUseCases {
        return PokemonUseCases(
            GetWeaknessesAndStrengths(typeRepository)
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @ViewModelScoped
    fun providerPokemonPager(
        pokemonNetworkService: IPokemonNetworkService,
        pokemonDao: PokemonDao,
    ): Pager<Int, PokemonNameEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
            ),
            remoteMediator = PokemonNameRemoteMediator(
                pokemonNetworkService = pokemonNetworkService,
                pokemonDao = pokemonDao,
            ),
            pagingSourceFactory = {
                pokemonDao.pagingSource()
            }
        )
    }
}
