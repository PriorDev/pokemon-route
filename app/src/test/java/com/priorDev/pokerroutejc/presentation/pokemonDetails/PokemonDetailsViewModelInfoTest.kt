package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.PokemonRepo
import com.priorDev.pokerroutejc.data.PokemonRepoFake
import com.priorDev.pokerroutejc.data.SettingsRepo
import com.priorDev.pokerroutejc.data.SettingsRepoFake
import com.priorDev.pokerroutejc.data.TypeRepoFake
import com.priorDev.pokerroutejc.domain.pokemon.useCases.GetDamageRelations
import com.priorDev.pokerroutejc.domain.pokemon.useCases.PokemonUseCases
import com.priorDev.pokerroutejc.domain.pokemonData
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.utils.GlobalEventChannelFake
import com.priorDev.pokerroutejc.utils.MainCoroutineExtension
import com.priorDev.pokerroutejc.utils.SavedStateHandleRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExtendWith(MainCoroutineExtension::class)
class PokemonDetailsViewModelInfoTest {
    private val pokemonRoute = Routes.PkDetails("pikachu")

    @RegisterExtension
    private val savedStateHandleRule = SavedStateHandleRule(pokemonRoute)
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var pokemonRepo: PokemonRepo
    private val pokemonRepoFake = PokemonRepoFake()

    private lateinit var settingsRepo: SettingsRepo
    private val settingsRepoFake = SettingsRepoFake()

    private lateinit var useCases: PokemonUseCases
    private lateinit var globalEvent: GlobalEventChannel

    @BeforeEach
    fun setUp() {
        savedStateHandle = savedStateHandleRule.savedStateHandleMock
        useCases = PokemonUseCases(GetDamageRelations(TypeRepoFake()))
        globalEvent = GlobalEventChannelFake()
        pokemonRepo = pokemonRepoFake
        settingsRepo = settingsRepoFake
    }

    @Test
    fun `Get pokemonId from savedStateHandle and get pokemon`() = runTest {
        val pokemonTest = pokemonData()
        pokemonRepoFake.getPokemonFlow = flowOf(ResourceFlow.Success(pokemonTest))

        val viewModel = PokemonDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = pokemonRepo,
            useCases = useCases,
            globalEvent = globalEvent,
            settingsRepo = settingsRepo
        )

        viewModel.states.test {
            testScheduler.advanceUntilIdle()
            awaitItem() // discard first emission

            val emission1 = awaitItem()
            assertThat(emission1.pokemon.id).isNotNull()
            assertThat(emission1.pokemon.name).isEqualTo(pokemonTest.name)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
