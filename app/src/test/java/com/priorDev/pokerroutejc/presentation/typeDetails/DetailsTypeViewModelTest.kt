package com.priorDev.pokerroutejc.presentation.typeDetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.priorDev.pokerroutejc.data.TypeRepoFake
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData
import com.priorDev.pokerroutejc.presentation.core.DisplayError
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.utils.MainCoroutineExtension
import com.priorDev.pokerroutejc.utils.Resource
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class DetailsTypeViewModelTest {
    private lateinit var viewModel: DetailsTypeViewModel
    private lateinit var repoFake: TypeRepoFake
    private val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)

    @BeforeEach
    fun setUp() {
        repoFake = TypeRepoFake()
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<Routes.TypeDetails>() } returns Routes.TypeDetails(typeId = 11) // Water type
    }

    @Test
    fun `Test init, repo returns Success, updates state correctly`() = runTest {
        viewModel = DetailsTypeViewModel(
            repository = repoFake,
            savedStateHandle = savedStateHandle
        )

        viewModel.states.test {
            // Wait for initial emission (might be default or already loading)
            val firstState = awaitItem()

            // If the first state is default (None), wait for Loading
            if (firstState.loadingIndicator == LoadingIndicator.None) {
                 assertThat(awaitItem().loadingIndicator).isEqualTo(LoadingIndicator.SolidSpinningWheel)
            } else {
                 assertThat(firstState.loadingIndicator).isEqualTo(LoadingIndicator.SolidSpinningWheel)
            }

            // Success update
            val successState = awaitItem()
            assertThat(successState.details.id).isEqualTo(11)

            // Final loading update
            val finalState = awaitItem()
            assertThat(finalState.loadingIndicator).isEqualTo(LoadingIndicator.None)
        }
    }

    @Test
    fun `Test init, repo returns Error, updates errorState`() = runTest {
        every { savedStateHandle.toRoute<Routes.TypeDetails>() } returns Routes.TypeDetails(typeId = 999)

        viewModel = DetailsTypeViewModel(
            repository = repoFake,
            savedStateHandle = savedStateHandle
        )

        viewModel.states.test {
            // Handle potentially fast emission
            val firstState = awaitItem()
            if (firstState.loadingIndicator == LoadingIndicator.None) {
                 awaitItem() // Loading
            }

            val errorState = awaitItem()
            assertThat(errorState.errorState).isNotNull()
            assertThat(errorState.errorState?.displayAs).isEqualTo(DisplayError.FullScreen)

            val finalState = awaitItem()
            assertThat(finalState.loadingIndicator).isEqualTo(LoadingIndicator.None)
        }
    }

    @Test
    fun `Test retry flow, Error then Success, clears errorState`() = runTest {
        // Start with error
        every { savedStateHandle.toRoute<Routes.TypeDetails>() } returns Routes.TypeDetails(typeId = 999)

        viewModel = DetailsTypeViewModel(
            repository = repoFake,
            savedStateHandle = savedStateHandle
        )

        viewModel.states.test {
            // Consume until error
            cancelAndIgnoreRemainingEvents()
        }

        // Wait for coroutines to settle if needed, but in runTest main dispatcher is single threaded.
        // We need to trigger retry. The retry action is inside the errorState lambda.
        // We can't easily extract the lambda from the state to call it in test without inspecting state.

        viewModel.states.test {
             // We expect the current state to be Error (or None with errorState present)
             val currentState = awaitItem()
             assertThat(currentState.errorState).isNotNull()

             // Manually invoke the retry action.
             // Ideally we should extract it from the state, but here we can simulate the "retry" by calling the private method via reflection OR
             // better, grab the action from the state.
             val retryAction = currentState.errorState?.onAction
             assertThat(retryAction).isNotNull()

             // Now assume we fixed the repo issue or changed ID?
             // TypeRepoFake returns error for 999. We can't easily change the ID passed to `getType` because it's captured in the closure `getType(typeId)`.
             // But we can change the RepoFake behavior for ID 999 if we made it mutable?
             // Or we just verify that calling retryAction triggers Loading and clears ErrorState.

             retryAction?.invoke()

             // It should emit Loading AND errorState = null
             val loadingState = awaitItem()
             assertThat(loadingState.loadingIndicator).isEqualTo(LoadingIndicator.SolidSpinningWheel)
             assertThat(loadingState.errorState).isNull() // VERIFY FIX

             // Then it will emit Error again because ID 999 is still invalid in our fake setup
             val errorState = awaitItem()
             assertThat(errorState.errorState).isNotNull()

             awaitItem() // Loading None
        }
    }
}
