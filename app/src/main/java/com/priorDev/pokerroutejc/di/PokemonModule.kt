package com.priorDev.pokerroutejc.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.priorDev.pokerroutejc.data.database.MyDataBase
import com.priorDev.pokerroutejc.data.database.PokemonDao
import com.priorDev.pokerroutejc.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.data.network.pokemon.PokemonNetService
import com.priorDev.pokerroutejc.domain.pokemon.PokemonNameRemoteMediator
import com.priorDev.pokerroutejc.domain.pokemon.PokemonNameRemoteMediator.Companion.PAGE_SIZE
import com.priorDev.pokerroutejc.domain.pokemon.useCases.GetWeaknessesAndStrengths
import com.priorDev.pokerroutejc.domain.pokemon.useCases.PokemonUseCases
import com.priorDev.pokerroutejc.data.TypeRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PokemonModule {
    @ViewModelScoped
    @Provides
    fun providerPokemonDao(db: MyDataBase) = db.pokemonDao

    @ViewModelScoped
    @Provides
    fun providerPokemonUseCases(
        typeRepository: TypeRepo
    ): PokemonUseCases {
        return PokemonUseCases(
            GetWeaknessesAndStrengths(typeRepository)
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @ViewModelScoped
    fun providerPokemonPager(
        pokemonNetService: PokemonNetService,
        pokemonDao: PokemonDao,
    ): Pager<Int, PokemonNameEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
            ),
            remoteMediator = PokemonNameRemoteMediator(
                pokemonNetService = pokemonNetService,
                pokemonDao = pokemonDao,
            ),
            pagingSourceFactory = {
                pokemonDao.pagingSource()
            }
        )
    }
}
