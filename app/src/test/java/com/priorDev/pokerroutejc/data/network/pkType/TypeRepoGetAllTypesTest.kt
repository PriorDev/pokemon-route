package com.priorDev.pokerroutejc.data.network.pkType

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.TypeRepoImp
import com.priorDev.pokerroutejc.data.database.TypeDao
import com.priorDev.pokerroutejc.data.database.toDB
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.data.database.TypeDaoFake
import com.priorDev.pokerroutejc.data.network.TypeNetServiceFake
import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeResponse
import com.priorDev.pokerroutejc.data.TypeRepo
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TypeRepoGetAllTypesTest {
    private lateinit var service: TypeNetService
    private lateinit var typeDao: TypeDao
    private lateinit var typeRepo: TypeRepo

    @BeforeEach
    fun setUp() {
        typeDao = TypeDaoFake()
        service = TypeNetServiceFake()
    }

    @Test
    fun `test getAllTypesFlow refresh FALSE, insert in DB, return SUCCESS`() =
        runTest {
            val containerResponse = ContainerTypeResponse(
                count = 1,
                types = listOf(
                    TypeResponse("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")
                )
            )

            val serviceFake = TypeNetServiceFake()
            serviceFake.getAllTypeResponse = NetworkResource.Success(containerResponse)

            typeRepo = TypeRepoImp(serviceFake, typeDao)

            typeRepo.getAllTypesFlow(false).test {
                // Validate first emission is loading with solid spinning wheel due is not refreshing
                val emission1 = awaitItem()
                assertThat(emission1).isInstanceOf(ResourceFlow.Loading::class)

                val emission1Loading = (emission1 as ResourceFlow.Loading).loadingIndicator
                assertThat(emission1Loading).isInstanceOf(LoadingIndicator.SolidSpinningWheel::class)

                // Validate second emission is success
                val emission2 = awaitItem()
                assertThat(emission2).isInstanceOf(ResourceFlow.Success::class)
                val typeList = (emission2 as ResourceFlow.Success).data
                assertThat(typeList?.size).isEqualTo(1)
                assertThat(typeList?.first()?.name).isEqualTo("bulbasaur")
                // Verify that type was inserted in DB
                assertThat(typeDao.getAllTypes().size).isEqualTo(1)

                // Validate third emission is loading of type none
                val emission3 = awaitItem()
                assertThat(emission3).isInstanceOf(ResourceFlow.Loading::class)

                val emission3Loading = (emission3 as ResourceFlow.Loading).loadingIndicator
                assertThat(emission3Loading).isInstanceOf(LoadingIndicator.None::class)

                awaitComplete()
            }
        }

    @Test
    fun `test getAllTypesFlow refresh TRUE, insert in DB, return SUCCESS`() =
        runTest {
            val containerResponse = ContainerTypeResponse(
                count = 1,
                types = listOf(
                    TypeResponse("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")
                )
            )

            val serviceFake = TypeNetServiceFake()
            serviceFake.getAllTypeResponse = NetworkResource.Success(containerResponse)

            typeRepo = TypeRepoImp(serviceFake, typeDao)

            typeRepo.getAllTypesFlow(true).test {
                // Validate first emission is loading with solid spinning wheel due is not refreshing
                val emission1 = awaitItem()
                assertThat(emission1).isInstanceOf(ResourceFlow.Loading::class)

                val emission1Loading = (emission1 as ResourceFlow.Loading).loadingIndicator
                assertThat(emission1Loading).isInstanceOf(LoadingIndicator.Refreshing::class)

                // Validate second emission is success
                val emission2 = awaitItem()
                assertThat(emission2).isInstanceOf(ResourceFlow.Success::class)
                val typeList = (emission2 as ResourceFlow.Success).data
                assertThat(typeList?.size).isEqualTo(1)
                assertThat(typeList?.first()?.name).isEqualTo("bulbasaur")
                // Verify that type was inserted in DB
                assertThat(typeDao.getAllTypes().size).isEqualTo(1)

                // Validate third emission is loading of type none
                val emission3 = awaitItem()
                assertThat(emission3).isInstanceOf(ResourceFlow.Loading::class)

                val emission3Loading = (emission3 as ResourceFlow.Loading).loadingIndicator
                assertThat(emission3Loading).isInstanceOf(LoadingIndicator.None::class)

                awaitComplete()
            }
        }

    @Test
    fun `test getAllTypesFlow refresh FALSE, empty DB, return FAIL`() =
        runTest {
            val serviceFake = TypeNetServiceFake()
            serviceFake.getAllTypeResponse = NetworkResource.Fail(
                NetworkError.ServerError(serverMessage = "Error getting types")
            )

            typeRepo = TypeRepoImp(serviceFake, typeDao)

            typeRepo.getAllTypesFlow(false).test {
                // Validate first emission is loading with solid spinning wheel due is not refreshing
                val emission1 = awaitItem()
                assertThat(emission1).isInstanceOf(ResourceFlow.Loading::class)

                val emission1Loading = (emission1 as ResourceFlow.Loading).loadingIndicator
                assertThat(emission1Loading).isInstanceOf(LoadingIndicator.SolidSpinningWheel::class)

                // Validate second emission is success
                val emission2 = awaitItem()
                assertThat(emission2).isInstanceOf(ResourceFlow.Error::class)
                val errorType = (emission2 as ResourceFlow.Error).networkErrorType
                assertThat(errorType).isInstanceOf(NetworkError.ServerError::class)
                // Verify that DB is empty
                assertThat(typeDao.getAllTypes().size).isEqualTo(0)

                // Validate third emission is loading of type none
                val emission3 = awaitItem()
                assertThat(emission3).isInstanceOf(ResourceFlow.Loading::class)

                val emission3Loading = (emission3 as ResourceFlow.Loading).loadingIndicator
                assertThat(emission3Loading).isInstanceOf(LoadingIndicator.None::class)

                awaitComplete()
            }
        }

    @Test
    fun `test getAllTypesFlow refresh FALSE, NO empty DB, return SUCCESS`() =
        runTest {
            val serviceFake = TypeNetServiceFake()
            serviceFake.getAllTypeResponse = NetworkResource.Fail(
                NetworkError.ServerError(serverMessage = "Error getting types")
            )

            val typeResponse = TypeResponse("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")

            val typeDao = TypeDaoFake()
            typeDao.insertTypes(
                listOf(typeResponse.toDB())
            )

            typeRepo = TypeRepoImp(serviceFake, typeDao)

            typeRepo.getAllTypesFlow(false).test {
                // Validate first emission is loading with solid spinning wheel due is not refreshing
                val emission1 = awaitItem()
                assertThat(emission1).isInstanceOf(ResourceFlow.Loading::class)

                val emission1Loading = (emission1 as ResourceFlow.Loading).loadingIndicator
                assertThat(emission1Loading).isInstanceOf(LoadingIndicator.SolidSpinningWheel::class)

                val emission2 = awaitItem()
                assertThat(emission2).isInstanceOf(ResourceFlow.Success::class)
                val typeList = (emission2 as ResourceFlow.Success).data
                assertThat(typeList?.size).isEqualTo(1)
                assertThat(typeList?.first()?.name).isEqualTo("bulbasaur")
                // Verify that DB is not empty
                assertThat(typeDao.getAllTypes().size).isEqualTo(1)

                // Validate third emission is loading of type none
                val emission3 = awaitItem()
                assertThat(emission3).isInstanceOf(ResourceFlow.Loading::class)

                val emission3Loading = (emission3 as ResourceFlow.Loading).loadingIndicator
                assertThat(emission3Loading).isInstanceOf(LoadingIndicator.None::class)

                awaitComplete()
            }
        }

    @Test
    fun `test getAllTypesFlow refresh TRUE, NO empty DB, return FAIL`() =
        runTest {
            val serviceFake = TypeNetServiceFake()
            serviceFake.getAllTypeResponse = NetworkResource.Fail(
                NetworkError.ServerError(serverMessage = "Error getting types")
            )

            val typeResponse = TypeResponse("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/")

            val typeDao = TypeDaoFake()
            typeDao.insertTypes(
                listOf(typeResponse.toDB())
            )

            typeRepo = TypeRepoImp(serviceFake, typeDao)

            typeRepo.getAllTypesFlow(true).test {
                // Validate first emission is loading with solid spinning wheel due is not refreshing
                val emission1 = awaitItem()
                assertThat(emission1).isInstanceOf(ResourceFlow.Loading::class)

                val emission1Loading = (emission1 as ResourceFlow.Loading).loadingIndicator
                assertThat(emission1Loading).isInstanceOf(LoadingIndicator.Refreshing::class)

                // Verify that return is fail
                val emission2 = awaitItem()
                assertThat(emission2).isInstanceOf(ResourceFlow.Error::class)
                // Verify that DB is not empty
                assertThat(typeDao.getAllTypes().size).isEqualTo(1)

                // Validate third emission is loading of type none
                val emission3 = awaitItem()
                assertThat(emission3).isInstanceOf(ResourceFlow.Loading::class)

                val emission3Loading = (emission3 as ResourceFlow.Loading).loadingIndicator
                assertThat(emission3Loading).isInstanceOf(LoadingIndicator.None::class)

                awaitComplete()
            }
        }
}
