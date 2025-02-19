package com.priorDev.pokerroutejc.featureTypes.presentation.list

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.TypeRepoFake
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.domain.GlobalEventChannelFake
import com.priorDev.pokerroutejc.presentation.typeList.ListTypeViewModel
import com.priorDev.pokerroutejc.presentation.typeList.ListTypesEvent
import com.priorDev.pokerroutejc.presentation.core.DisplayError
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.utils.MainCoroutineExtension
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class ListTypeViewModelTest {
    private lateinit var listTypeViewModel: ListTypeViewModel
    private lateinit var globalEventChannel: GlobalEventChannel

    @BeforeEach
    fun setUp() {
        globalEventChannel = GlobalEventChannelFake()
    }

    @Test
    fun `Test getAllTypesFlow, repo return Loading, update state`() = runTest {
        val repoFake = TypeRepoFake()
        repoFake.getAllTypeFlow = flow {
            emit(
                ResourceFlow.Loading(loadingIndicator = LoadingIndicator.SolidSpinningWheel)
            )
        }

        listTypeViewModel = ListTypeViewModel(
            repository = repoFake,
            globalEvent = globalEventChannel
        )

        listTypeViewModel.onEvent(ListTypesEvent.Refresh)

        listTypeViewModel.screenStates.test {
            // Discard initial state
            awaitItem()

            val emission1 = awaitItem()
            assertThat(emission1.loadingIndicator)
                .isInstanceOf(LoadingIndicator.SolidSpinningWheel::class)
        }
    }

    @Test
    fun `Test getAllTypesFlow, repo return UnableToConnect, error display as dialog`() = runTest {
        val repoFake = TypeRepoFake()
        repoFake.getAllTypeFlow = flow {
            emit(
                ResourceFlow.Error(networkErrorType = NetworkError.UnableToConnect)
            )
        }

        listTypeViewModel = ListTypeViewModel(
            repository = repoFake,
            globalEvent = globalEventChannel
        )

        listTypeViewModel.onEvent(ListTypesEvent.Refresh)

        listTypeViewModel.screenStates.test {
            // Discard initial state
            awaitItem()

            val emission1 = awaitItem()
            assertThat(emission1.error.displayAs)
                .isInstanceOf(DisplayError.Dialog::class.java)
        }
    }
}
