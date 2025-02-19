package com.priorDev.pokerroutejc.data.network.pkType

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.database.TypeDao
import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.data.network.fakes.TypeDaoFake
import com.priorDev.pokerroutejc.data.network.fakes.TypeServiceFake
import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeResponse
import com.priorDev.pokerroutejc.featureTypes.domain.TypeRepository
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TypeRepositoryImpTest {
    private lateinit var service: ITypeService
    private lateinit var typeDao: TypeDao
    private lateinit var typeRepo: TypeRepository

    @BeforeEach
    fun setUp() {
        typeDao = TypeDaoFake()
        service = TypeServiceFake()
    }

    @Test
    fun `test getAllTypesFlow refresh false as success, insert types in DB, return Resource Flow success`() =
        runTest {
            val mockResponse: HttpResponse = mockk {
                coEvery { body<ContainerTypeResponse>() } returns ContainerTypeResponse(
                    count = 1,
                    types = listOf(
                        TypeResponse("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")
                    )
                )
            }

            val serviceFake = TypeServiceFake()
            serviceFake.returnNetworkResource = NetworkResource.Success(
                mockResponse
            )
            typeRepo = TypeRepositoryImp(serviceFake, typeDao)


            typeRepo.getAllTypesFlow(false).test {
                val emission1 = awaitItem()
                assertThat(emission1 is ResourceFlow.Loading).isTrue()
                val emission2 = awaitItem()
                val emission3 = awaitItem()
                awaitComplete()
            }
        }
}