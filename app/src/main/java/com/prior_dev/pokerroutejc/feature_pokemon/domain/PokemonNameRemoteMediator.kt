package com.prior_dev.pokerroutejc.feature_pokemon.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.prior_dev.pokerroutejc.core.MakeNetworkCall
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.data.database.MyDataBase
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonService
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonDao
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonNameEntity
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.toDB
import com.prior_dev.pokerroutejc.utils.orZero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.Duration

@OptIn(ExperimentalPagingApi::class)
class PokemonNameRemoteMediator(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao,
    private val makeNetworkCall: MakeNetworkCall,
): RemoteMediator<Int, PokemonNameEntity>() {

    private var isFirstTime = true

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonNameEntity>
    ): MediatorResult {
        return when (loadType) {
            LoadType.REFRESH -> {
                if (isFirstTime) {
                    isFirstTime = false
                    MediatorResult.Success(endOfPaginationReached = false)
                } else {
                    pokemonDao.eraseNames()
                    fetchData(state)
                }
            }

            LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> fetchData(state)
        }
    }

    private suspend fun fetchData(
        state: PagingState<Int, PokemonNameEntity>
    ): MediatorResult {
        val pokemonExpiredData = withContext(Dispatchers.Default) {
            pokemonDao.getExpiredPokemons(
                threshold = THRESHOLD.toMillis(),
            )
        }

        val minIdExpired = pokemonExpiredData.minByOrNull { it.id - 1 }?.id

        val offset = minIdExpired
            ?: state.lastItemOrNull()?.id
            ?: 0

        val response = makeNetworkCall {
            pokemonService.getAllPokemons(
                urlLimitOffset = "pokemon?offset=$offset&limit=${state.config.pageSize}"
            )
        }

        return when (response) {
            is Resource.Loading -> MediatorResult.Error(Throwable("Unknown error"))

            is Resource.Error -> {
                MediatorResult.Error(response.throwable)
            }

            is Resource.Success -> {
                val pokemons = response.data?.pokemons?.map { it.toDB() }

                if (pokemons != null) {
                    pokemonDao.insert(pokemons)
                }

                MediatorResult.Success(
                    endOfPaginationReached = response.data?.next.isNullOrEmpty()
                )
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
        val THRESHOLD = Duration.ofDays(10)
    }
}