package com.prior_dev.pokerroutejc.feature_pokemon.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.prior_dev.pokerroutejc.core.MakeNetworkCall
import com.prior_dev.pokerroutejc.data.database.MyDataBase
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonService
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonDao
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonNameEntity
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.PokemonApi
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonNameRemoteMediator
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonNameRemoteMediator.Companion.PAGE_SIZE
import com.prior_dev.pokerroutejc.feature_pokemon.domain.use_cases.GetWeaknessesAndStrengths
import com.prior_dev.pokerroutejc.feature_pokemon.domain.use_cases.PokemonUseCases
import com.prior_dev.pokerroutejc.feature_types.domain.TypeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import javax.inject.Singleton

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
        typeRepository: TypeRepository
    ): PokemonUseCases {
        return PokemonUseCases(
            GetWeaknessesAndStrengths(typeRepository)
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @ViewModelScoped
    fun providerPokemonPager(
        pokemonService: PokemonService,
        pokemonDao: PokemonDao,
        pokemonDb: MyDataBase,
        makeNetworkCall: MakeNetworkCall
    ): Pager<Int, PokemonNameEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
            ),
            remoteMediator = PokemonNameRemoteMediator(
                pokemonService = pokemonService,
                pokemonDao = pokemonDao,
                pokemonDb = pokemonDb,
                makeNetworkCall = makeNetworkCall
            ),
            pagingSourceFactory = {
                pokemonDao.pagingSource()
            }
        )
    }
}