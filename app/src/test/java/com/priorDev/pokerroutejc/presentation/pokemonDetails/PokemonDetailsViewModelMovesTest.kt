package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotEmpty
import assertk.assertions.isTrue
import com.priorDev.pokerroutejc.data.PokemonRepo
import com.priorDev.pokerroutejc.data.PokemonRepoFake
import com.priorDev.pokerroutejc.data.SettingsRepo
import com.priorDev.pokerroutejc.data.SettingsRepoFake
import com.priorDev.pokerroutejc.data.TypeRepoFake
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.domain.moveBite
import com.priorDev.pokerroutejc.domain.moveCrunch
import com.priorDev.pokerroutejc.domain.moveDetailsList
import com.priorDev.pokerroutejc.domain.moveFlail
import com.priorDev.pokerroutejc.domain.moveIceFang
import com.priorDev.pokerroutejc.domain.moveLeer
import com.priorDev.pokerroutejc.domain.moveScaryFace
import com.priorDev.pokerroutejc.domain.moveScratch
import com.priorDev.pokerroutejc.domain.moveTackle
import com.priorDev.pokerroutejc.domain.moveWaterGun
import com.priorDev.pokerroutejc.domain.pokemon.useCases.GetDamageRelations
import com.priorDev.pokerroutejc.domain.pokemon.useCases.PokemonUseCases
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.presentation.core.DisplayError
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.MoveFilterModel
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.utils.ApiLanguages
import com.priorDev.pokerroutejc.utils.FlowSource
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.utils.GlobalEventChannelFake
import com.priorDev.pokerroutejc.utils.MainCoroutineExtension
import com.priorDev.pokerroutejc.utils.Resource
import com.priorDev.pokerroutejc.utils.SavedStateHandleRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExtendWith(MainCoroutineExtension::class)
class PokemonDetailsViewModelMovesTest {
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
    fun `General test when successfully get moves`() = runTest {
        val moveMap = mapOf("level-up" to moveDetailsList())

        pokemonRepoFake.getPkMoves = Resource.Success(moveMap)
        val viewModel = PokemonDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = pokemonRepo,
            useCases = useCases,
            globalEvent = globalEvent,
            settingsRepo = settingsRepo
        )

        viewModel.pkMovesStates.test {
            testScheduler.advanceUntilIdle()
            awaitItem() // discard first emission

            val emission1 = awaitItem()
            assertThat(emission1.loading).isEqualTo(LoadingIndicator.SolidSpinningWheel)

            assertThat(viewModel.moves.size).isEqualTo(moveMap.size)
            assertThat(emission1.moveCriteria).isEmpty()

            val emission2 = awaitItem()
            assertThat(emission2.moveCriteria).isNotEmpty()
            assertThat(emission2.loading).isEqualTo(LoadingIndicator.None)
        }
    }

    @Test
    fun `Test get filters based on moves`() = runTest {
        val moveMap = mapOf(
            "level-up" to listOf(
                moveBite().copy(type = TypeData(17, "dark")),
                moveCrunch().copy(type = TypeData(17, "dark")),
                moveWaterGun().copy(type = TypeData(1, "water")),
            )
        )

        pokemonRepoFake.getPkMoves = Resource.Success(moveMap)
        val viewModel = PokemonDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = pokemonRepo,
            useCases = useCases,
            globalEvent = globalEvent,
            settingsRepo = settingsRepo
        )

        viewModel.pkMovesStates.test {
            testScheduler.advanceUntilIdle()
            awaitItem() // discard first emission

            val emission1 = awaitItem()
            assertThat(emission1.moveCriteria).isEmpty()

            val emission2 = awaitItem()
            assertThat(emission2.moveCriteria.size).isEqualTo(2)
            assertThat(emission2.moveCriteria.filter { it.checked }).isEmpty()
        }
    }

    @Test
    fun `General test failure get moves`() = runTest {
        pokemonRepoFake.getPkMoves = Resource.Error(
            networkErrorType = NetworkError.UnableToConnect
        )

        val viewModel = PokemonDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = pokemonRepo,
            useCases = useCases,
            globalEvent = globalEvent,
            settingsRepo = settingsRepo
        )

        viewModel.pkMovesStates.test {
            testScheduler.advanceUntilIdle()
            awaitItem() // discard first emission

            val emission1 = awaitItem()
            assertThat(emission1.loading).isEqualTo(LoadingIndicator.SolidSpinningWheel)

            assertThat(viewModel.moves.size).isEqualTo(0)
            assertThat(emission1.moveCriteria).isEmpty()

            val emission2 = awaitItem()
            assertThat(emission2.moveCriteria).isEmpty()
            assertThat(emission2.errorState?.displayAs).isEqualTo(DisplayError.FullScreen)
            assertThat(emission2.errorState?.isActionButtonVisible).isEqualTo(true)
            assertThat(emission2.errorState?.isDismissButtonVisible).isEqualTo(false)

            val emission3 = awaitItem()
            assertThat(emission3.loading).isEqualTo(LoadingIndicator.None)
        }
    }

    @Test
    fun `Test toggle MOVE and update moves visibility`() = runTest {
        val moveMap = mapOf(
            "level-up" to listOf(
                moveBite().copy(type = TypeData(17, "dark")),
                moveCrunch().copy(type = TypeData(17, "dark")),
                moveWaterGun().copy(type = TypeData(1, "water")),
                moveLeer().copy(type = TypeData(2, "fire"))
            )
        )

        pokemonRepoFake.getPkMoves = Resource.Success(moveMap)
        val viewModel = PokemonDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = pokemonRepo,
            useCases = useCases,
            globalEvent = globalEvent,
            settingsRepo = settingsRepo
        )

        viewModel.pkMovesStates.test {
            testScheduler.advanceUntilIdle()
            awaitItem() // discard first emission
            awaitItem() // discard solid loading emission
            awaitItem() // discard none loading emission

            // Change dark type moves to visible true
            viewModel.onEvent(
                PokemonDetailsEvents.ToggleMoveFilterCheck(
                    MoveFilterModel(checked = true, type = TypeData(17, "dark"))
                )
            )

            val emission1 = awaitItem()
            assertThat(emission1.moveCriteria.filter { it.checked }).hasSize(1)
            assertThat(emission1.moveCriteria.filter { it.checked.not() }).hasSize(2)

            val moveList1 = viewModel.moves.flatMap { it.value }
            assertThat(moveList1.filter { it.visible }).hasSize(2)
            assertThat(moveList1.filter { it.visible.not() }).hasSize(2)

            // Change fire type moves to visible true
            viewModel.onEvent(
                PokemonDetailsEvents.ToggleMoveFilterCheck(
                    MoveFilterModel(checked = true, type = TypeData(2, "fire"))
                )
            )

            val emission2 = awaitItem()
            assertThat(emission2.moveCriteria.filter { it.checked }).hasSize(2)
            assertThat(emission2.moveCriteria.filter { it.checked.not() }).hasSize(1)

            val moveList2 = viewModel.moves.flatMap { it.value }
            assertThat(moveList2.filter { it.visible }).hasSize(3)
            assertThat(moveList2.filter { it.visible.not() }).hasSize(1)

            // Uncheck dark and fire type moves
            viewModel.onEvent(
                PokemonDetailsEvents.ToggleMoveFilterCheck(
                    MoveFilterModel(checked = false, type = TypeData(17, "dark"))
                )
            )
            awaitItem()

            viewModel.onEvent(
                PokemonDetailsEvents.ToggleMoveFilterCheck(
                    MoveFilterModel(checked = false, type = TypeData(2, "fire"))
                )
            )

            val emission3 = awaitItem()
            assertThat(emission3.moveCriteria.filter { it.checked }).isEmpty()

            val moveList3 = viewModel.moves.flatMap { it.value }
            assertThat(moveList3.filter { it.visible }).hasSize(4)
        }
    }

    @Test
    fun `Test toggle LEARN METHOD and update moves visibility`() = runTest {
        val moveMap = mapOf(
            "level-up" to listOf(moveBite(), moveCrunch(), moveWaterGun()),
            "egg" to listOf(moveTackle(), moveIceFang(), moveScaryFace()),
            "MT" to listOf(moveScratch(), moveLeer(), moveFlail()),
        )

        pokemonRepoFake.getPkMoves = Resource.Success(moveMap)
        val viewModel = PokemonDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = pokemonRepo,
            useCases = useCases,
            globalEvent = globalEvent,
            settingsRepo = settingsRepo
        )

        viewModel.pkMovesStates.test {
            testScheduler.advanceUntilIdle()
            awaitItem() // discard first emission
            awaitItem() // discard solid loading emission
            awaitItem() // discard none loading emission

            // Uncheck egg learn method
            viewModel.onEvent(
                PokemonDetailsEvents.ToggleLearnMethodExpand(
                    isExpanded = false,
                    learnMethod = "egg"
                )
            )

            val moveList1 = viewModel.moves.flatMap { it.value }
            assertThat(moveList1.filter { it.visible }).hasSize(6)
            assertThat(moveList1.filter { it.visible.not() }).hasSize(3)

            // Uncheck MT learn method
            viewModel.onEvent(
                PokemonDetailsEvents.ToggleLearnMethodExpand(
                    isExpanded = false,
                    learnMethod = "MT"
                )
            )

            val moveList2 = viewModel.moves.flatMap { it.value }
            assertThat(moveList2.filter { it.visible }).hasSize(3)
            assertThat(moveList2.filter { it.visible.not() }).hasSize(6)

            // Check egg learn method
            viewModel.onEvent(
                PokemonDetailsEvents.ToggleLearnMethodExpand(
                    isExpanded = true,
                    learnMethod = "egg"
                )
            )

            val moveList3 = viewModel.moves.flatMap { it.value }
            assertThat(moveList3.filter { it.visible }).hasSize(6)
            assertThat(moveList3.filter { it.visible.not() }).hasSize(3)

            // Check MT learn method
            viewModel.onEvent(
                PokemonDetailsEvents.ToggleLearnMethodExpand(
                    isExpanded = true,
                    learnMethod = "MT"
                )
            )

            val moveList4 = viewModel.moves.flatMap { it.value }
            assertThat(moveList4.filter { it.visible }).hasSize(9)
            assertThat(moveList4.filter { it.visible.not() }).hasSize(0)
        }
    }

    @Test
    fun `Test change language should reload moves`() = runTest {
        val moveMapFirstCall = mapOf("level-up" to moveDetailsList())
        val moveMapSecondCall = mapOf("Subir de nivel" to listOf(moveScratch()))

        pokemonRepoFake.getPkMoves = Resource.Success(moveMapFirstCall)

        val settingsRepoMockk = mockk<SettingsRepo>()
        val settingFlowMockk = FlowSource<ApiLanguages>()
        coEvery { settingsRepoMockk.getAppLanguage() } returns settingFlowMockk.flow
        coEvery { settingsRepoMockk.updateLanguage(any()) } returns Unit

        val viewModel = PokemonDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = pokemonRepo,
            useCases = useCases,
            globalEvent = globalEvent,
            settingsRepo = settingsRepoMockk
        )

        viewModel.pkMovesStates.test {
            settingFlowMockk.emit(ApiLanguages.ENGLISH)
            testScheduler.advanceUntilIdle()
            awaitItem() // discard first emission

            val emission1 = awaitItem()
            assertThat(emission1.loading).isEqualTo(LoadingIndicator.SolidSpinningWheel)

            assertThat(viewModel.moves.size).isEqualTo(moveMapFirstCall.size)
            assertThat(viewModel.moves.containsKey("level-up")).isTrue()
            assertThat(emission1.moveCriteria).isEmpty()

            val emission2 = awaitItem()
            assertThat(emission2.moveCriteria).isNotEmpty()
            assertThat(emission2.loading).isEqualTo(LoadingIndicator.None)

            viewModel.onEvent(
                PokemonDetailsEvents.SelectLanguage(ApiLanguages.SPANISH)
            )

            pokemonRepoFake.getPkMoves = Resource.Success(moveMapSecondCall)
            settingFlowMockk.emit(ApiLanguages.SPANISH)

            val emission3 = awaitItem()
            assertThat(emission3.loading).isEqualTo(LoadingIndicator.SolidSpinningWheel)

            assertThat(viewModel.moves.size).isEqualTo(moveMapSecondCall.size)
            assertThat(viewModel.moves.containsKey("Subir de nivel")).isTrue()
            assertThat(viewModel.moves.containsKey("leve up")).isFalse()

            val emission4 = awaitItem()
            assertThat(emission4.moveCriteria).hasSize(1)
            assertThat(emission4.loading).isEqualTo(LoadingIndicator.None)
        }
    }
}
