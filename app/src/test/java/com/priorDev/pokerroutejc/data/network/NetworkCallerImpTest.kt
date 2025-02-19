package com.priorDev.pokerroutejc.data.network

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeResponse
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.data.network.utils.NetworkResource
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.util.reflect.typeInfo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NetworkCallerImpTest {
    private lateinit var networkCaller: NetworkCaller

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        networkCaller = NetworkCallerImp(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Make a network call and get a 200 status code and validate the response`() = runTest {
        val typeResponse = TypeResponse("bulbasaur", "url")

        val mockResponse: HttpResponse = mockk {
            coEvery { status } returns HttpStatusCode.OK
            coEvery { body<TypeResponse>() } returns typeResponse
        }

        val result = networkCaller.invoke<TypeResponse>(typeInfo<TypeResponse>()) {
            mockResponse
        }

        advanceUntilIdle()

        assertThat(result).isInstanceOf(NetworkResource.Success::class)
        val successResult = result as NetworkResource.Success

        assertThat(successResult.data).isInstanceOf(TypeResponse::class)
        assertThat(successResult.data.name).isEqualTo(typeResponse.name)
        assertThat(successResult.data.url).isEqualTo(typeResponse.url)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Make a network call and get a 400 status code`() = runTest {
        val mockResponse: HttpResponse = mockk {
            coEvery { status } returns HttpStatusCode.BadRequest
        }

        val result = networkCaller.invoke<Unit>(typeInfo<Unit>()) {
            mockResponse
        }

        advanceUntilIdle()

        assertThat(result is NetworkResource.Fail).isTrue()
        val failResult = result as NetworkResource.Fail

        assertThat(failResult.error is NetworkError.ClientError).isTrue()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Make a network call and get a 500 status code`() = runTest {
        val mockResponse: HttpResponse = mockk {
            coEvery { status } returns HttpStatusCode.InternalServerError
        }

        val result = networkCaller.invoke<Unit>(typeInfo<Unit>()) {
            mockResponse
        }

        advanceUntilIdle()

        assertThat(result is NetworkResource.Fail).isTrue()
        val failResult = result as NetworkResource.Fail

        assertThat(failResult.error is NetworkError.ServerError).isTrue()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Make a network call and get a 1000 status code`() = runTest {
        val mockResponse: HttpResponse = mockk {
            coEvery { status } returns HttpStatusCode(1000, "Unknown Error")
        }

        val result = networkCaller.invoke<Unit>(typeInfo<Unit>()) {
            mockResponse
        }

        advanceUntilIdle()

        assertThat(result is NetworkResource.Fail).isTrue()
        val failResult = result as NetworkResource.Fail

        assertThat(failResult.error is NetworkError.UnknownError).isTrue()
    }
}
