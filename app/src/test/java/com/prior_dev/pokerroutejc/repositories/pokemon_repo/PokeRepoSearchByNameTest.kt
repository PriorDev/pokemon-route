package com.prior_dev.pokerroutejc.repositories.pokemon_repo

import com.prior_dev.pokerroutejc.core.MakeNetworkCall
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonRepositoryImp
import com.prior_dev.pokerroutejc.feature_pokemon.data.PokemonService
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonDao
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonNameEntity
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.AbilityDetailsResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.ContainerPokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.MoveDetailsResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonNameResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokeRepoSearchByNameTest {
    @Test
    fun searchPokemonNameByMatchSuccessWithNoLocalData() = runTest {
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

            override suspend fun getAbility(abilty: String): AbilityDetailsResponse? {
                TODO("Not yet implemented")
            }
        }
        val service = FakeService()

        class FakeDao: PokemonDao{
            var insert = false
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                insert = true
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                return if(insert){
                    listOf(
                        PokemonNameEntity(1, ""),
                        PokemonNameEntity(1, ""),
                        PokemonNameEntity(1, ""),
                    )
                }else{
                    emptyList()
                }
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }
        }
        val dao = FakeDao()
        val testDispatcher = StandardTestDispatcher(testScheduler)

        val repo = PokemonRepositoryImp(service, dao, MakeNetworkCall(testDispatcher))

        val resourceFlow = repo.searchPokemonNameByMatch("test").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        assertEquals(false, isNotLoadingResource.isLoading)

        assertEquals(true, dao.insert)

        val responseSuccess = resourceFlow.filter { it is Resource.Success }
        assertEquals(1, responseSuccess.count())
    }

    @Test
    fun searchPokemonNameByMatchSuccessWithLocalData() = runTest {
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

            override suspend fun getAbility(abilty: String): AbilityDetailsResponse? {
                TODO("Not yet implemented")
            }
        }
        val service = FakeService()

        class FakeDao: PokemonDao{
            var insert = false
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                insert = true
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                return listOf(
                    PokemonNameEntity(1, "fire"),
                    PokemonNameEntity(2, "ice"),
                    PokemonNameEntity(3, "poison"),
                )
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }
        }
        val dao = FakeDao()
        val testDispatcher = StandardTestDispatcher(testScheduler)

        val repo = PokemonRepositoryImp(service, dao, MakeNetworkCall(testDispatcher))

        val resourceFlow = repo.searchPokemonNameByMatch("test").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        assertEquals(false, isNotLoadingResource.isLoading)

        val responseSuccess = resourceFlow.filter { it is Resource.Success }
        assertEquals(1, responseSuccess.count())

        val pokemon = responseSuccess.first() as Resource.Success
        assertEquals(3, pokemon.data!!.count())
    }

    @Test
    fun searchPokemonNameByMatchFailWithLocalData() = runTest {
        class FakeService: PokemonService{
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                throw Exception("Error")
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getAbility(abilty: String): AbilityDetailsResponse? {
                TODO("Not yet implemented")
            }
        }
        val service = FakeService()

        class FakeDao: PokemonDao{
            var insert = false
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                insert = true
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                return listOf(
                    PokemonNameEntity(1, "fire"),
                    PokemonNameEntity(2, "ice"),
                    PokemonNameEntity(3, "poison"),
                )
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }
        }
        val dao = FakeDao()
        val testDispatcher = StandardTestDispatcher(testScheduler)

        val repo = PokemonRepositoryImp(service, dao, MakeNetworkCall(testDispatcher))

        val resourceFlow = repo.searchPokemonNameByMatch("test").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        assertEquals(false, isNotLoadingResource.isLoading)

        val responseSuccess = resourceFlow.filter { it is Resource.Success }
        assertEquals(1, responseSuccess.count())

        val pokemon = responseSuccess.first() as Resource.Success
        assertEquals(3, pokemon.data!!.count())
    }

    @Test
    fun searchPokemonNameByMatchFailWithNoLocalData() = runTest {
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

            override suspend fun getAbility(abilty: String): AbilityDetailsResponse? {
                TODO("Not yet implemented")
            }
        }
        val service = FakeService()

        class FakeDao: PokemonDao{
            var insert = false
            override suspend fun insert(pokemons: List<PokemonNameEntity>) {
                insert = true
            }

            override suspend fun getPokemonNameByMatch(name: String): List<PokemonNameEntity> {
                return emptyList()
            }

            override suspend fun eraseNames() {
                TODO("Not yet implemented")
            }
        }
        val dao = FakeDao()
        val testDispatcher = StandardTestDispatcher(testScheduler)

        val repo = PokemonRepositoryImp(service, dao, MakeNetworkCall(testDispatcher))

        val resourceFlow = repo.searchPokemonNameByMatch("test").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        assertEquals(false, isNotLoadingResource.isLoading)

        assertEquals(false, dao.insert)

        val response = resourceFlow.filter { it is Resource.Error || it is Resource.Success }
        assertEquals(0, response.count())
    }
}