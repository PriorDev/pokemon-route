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
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonResponse
import com.prior_dev.pokerroutejc.feature_pokemon.domain.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokeRepoGetAbility {
    @Test
    fun getAbilitySuccess() = runTest{
        class FakeService: PokemonService{
            val ability = AbilityDetailsResponse(
                name = "",
                effect_entries = emptyList()
            )
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getAbility(abilty: String): AbilityDetailsResponse? {
                return ability
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

        val resourceFlow = repo.getAbility("clorofila").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        Assert.assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        Assert.assertEquals(false, isNotLoadingResource.isLoading)

        val resourceSuccess = resourceFlow.first { it is Resource.Success } as Resource.Success
        Assert.assertEquals(service.ability.toDomain(), resourceSuccess.data)
    }

    @Test
    fun getAbilityFail() = runTest{
        class FakeService: PokemonService{
            val ability = AbilityDetailsResponse(
                name = "",
                effect_entries = emptyList()
            )
            override suspend fun getAllPokemons(urlLimitOffset: String): ContainerPokemonNameResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getPokemon(pokemon: String): PokemonResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getMoveDetails(move: Long): MoveDetailsResponse? {
                TODO("Not yet implemented")
            }

            override suspend fun getAbility(abilty: String): AbilityDetailsResponse? {
                throw Exception("Error")
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

        val resourceFlow = repo.getAbility("clorofila").toList()

        val isLoadingResource = resourceFlow.first() as Resource.Loading
        Assert.assertEquals(true, isLoadingResource.isLoading)

        val isNotLoadingResource = resourceFlow.last() as Resource.Loading
        Assert.assertEquals(false, isNotLoadingResource.isLoading)

        val resourceError = resourceFlow.first { it is Resource.Error } as Resource.Error
    }
}