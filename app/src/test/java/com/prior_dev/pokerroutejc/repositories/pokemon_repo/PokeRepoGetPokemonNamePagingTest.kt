package com.prior_dev.pokerroutejc.repositories.pokemon_repo

import com.prior_dev.pokerroutejc.core.MakeNetworkCall
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonRepositoryImp
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonService
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonDao
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonNameEntity
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.ContainerPokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.MoveDetailsResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokeRepoGetPokemonNamePagingTest {
    @Test
    fun getPokemonNamePagingSuccess() = runTest {
        class FakeService: PokemonService{
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                return ContainerPokemonNameResponse(
                    next = null,
                    pokemons = listOf(
                        PokemonNameResponse("https://pokeapi.co/api/v2/pokemon/1/", "bulbasaur"),
                        PokemonNameResponse("https://pokeapi.co/api/v2/pokemon/2/", "ivysaur"),
                        PokemonNameResponse("https://pokeapi.co/api/v2/pokemon/3/", "venusaur"),
                    )
                )
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                TODO("Not yet implemented")
            }

        }
        val service = FakeService()

        class FakeDao: PokemonDao{
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                TODO("Not yet implemented")
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                TODO("Not yet implemented")
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }

        }
        val dao = FakeDao()
        val testDispatcher = StandardTestDispatcher(testScheduler)

        val repo = PokemonRepositoryImp(service, dao, MakeNetworkCall(testDispatcher))

        val resourceFlow = repo.getPokemonNamePaging(10).toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        Assert.assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        Assert.assertEquals(false, isNotLoadingResource.isLoading)

        val responseSuccess = resourceFlow.filter { it is Resource.Success }
        Assert.assertEquals(1, responseSuccess.count())

        val names = (responseSuccess.first() as Resource.Success).data
        Assert.assertEquals(3, names!!.count())
    }

    @Test
    fun getPokemonNamePagingFail() = runTest {
        class FakeService: PokemonService{
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                throw Exception("")
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                TODO("Not yet implemented")
            }

        }
        val service = FakeService()

        class FakeDao: PokemonDao{
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                TODO("Not yet implemented")
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                TODO("Not yet implemented")
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }

        }
        val dao = FakeDao()
        val testDispatcher = StandardTestDispatcher(testScheduler)

        val repo = PokemonRepositoryImp(service, dao, MakeNetworkCall(testDispatcher))

        val resourceFlow = repo.getPokemonNamePaging(10).toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        Assert.assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        Assert.assertEquals(false, isNotLoadingResource.isLoading)

        val responseError = resourceFlow.filter { it is Resource.Error }
        Assert.assertEquals(1, responseError.count())
    }
}