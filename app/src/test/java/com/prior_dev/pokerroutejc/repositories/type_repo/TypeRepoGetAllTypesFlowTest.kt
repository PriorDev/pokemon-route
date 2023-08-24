package com.prior_dev.pokerroutejc.repositories.type_repo

import com.prior_dev.pokerroutejc.core.MakeNetworkCall
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_types.data.TypeRepositoryImp
import com.prior_dev.pokerroutejc.feature_types.data.TypeService
import com.prior_dev.pokerroutejc.feature_types.data.database.DamageRelationsEntity
import com.prior_dev.pokerroutejc.feature_types.data.database.TypeDao
import com.prior_dev.pokerroutejc.feature_types.data.database.TypeEntity
import com.prior_dev.pokerroutejc.feature_types.data.network.response.ContainerTypeResponse
import com.prior_dev.pokerroutejc.feature_types.data.network.response.DamageRelationsResponse
import com.prior_dev.pokerroutejc.feature_types.data.network.response.TypeDetailsResponse
import com.prior_dev.pokerroutejc.feature_types.data.network.response.TypeResponse
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeRepoGetAllTypesFlowTest {
    @Test
    fun getAllTypesFlowTestSuccessWithNoDataInDb(): Unit = runBlocking{
        class FakeService: TypeService {
            override suspend fun getAllTypes(): ContainerTypeResponse {
                return ContainerTypeResponse(
                    count = 1,
                    listOf(
                        TypeResponse(name = "fire", url = "fire/1/"),
                        TypeResponse(name = "ice", url = "ice/2/"),
                        TypeResponse(name = "ground", url = "ground/3/"),
                        TypeResponse(name = "poison", url = "poison/4/"),
                        TypeResponse(name = "steel", url = "steel/6/"),
                    )
                )
            }

            override suspend fun getType(typeId: Int): TypeDetailsResponse {
                val typeList = listOf(
                    TypeResponse(name = "fire", url = "fire/1"),
                    TypeResponse(name = "ice", url = "ice/2"),
                )

                return TypeDetailsResponse(
                    id = 1,
                    name ="fire",
                    damageRelationsResponse = DamageRelationsResponse(
                        doubleDamageFrom = typeList,
                        doubleDamageTo = emptyList(),
                        halfDamageFrom = typeList,
                        halfDamageTo = emptyList(),
                        noDamageFrom = typeList,
                        noDamageTo = emptyList()
                    )
                )
            }
        }

        class FakeDao: TypeDao {

            var typeList = emptyList<TypeEntity>()

            override suspend fun getAllTypes(): List<TypeEntity> {
                return typeList
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {
                typeList = types
            }

            override suspend fun getDamageRelationByTypeId(typeId: Int): List<DamageRelationsEntity> {
                return listOf(
                    DamageRelationsEntity(
                        id = 1,
                        ownType = "ice",
                        ownTypeId = 1,
                        doubleDamageTo = null,
                        doubleDamageFrom = null,
                        halfDamageTo = null,
                        halfDamageFrom = null,
                        noDamageTo = null,
                        noDamageFrom = null
                    )
                )
            }

            override suspend fun insertDamageRelations(damageRelationsEntity: List<DamageRelationsEntity>) {

            }

            override suspend fun deleteDamageRelation(typeId: Int) {

            }
        }

        val service = FakeService()
        val dao = FakeDao()

        val repo = TypeRepositoryImp(
            service = service,
            dao = dao,
            makeNetworkCall = MakeNetworkCall()
        )

        val resourceFlow = repo.getAllTypesFlow().toList()

        val firstIsLoading = resourceFlow.first() as Resource.Loading
        assertEquals(true, firstIsLoading.isLoading)

        val lastIsNotLoading = resourceFlow.last() as Resource.Loading
        assertEquals(false, lastIsNotLoading.isLoading)

        val resourceSuccess = resourceFlow.filter { it is Resource.Success }
        val types = (resourceSuccess.first() as Resource.Success).data!!

        assertEquals(1, resourceSuccess.count())
        assertEquals(dao.getAllTypes().count(), types.count())
    }

    @Test
    fun getAllTypesFlowTestSuccessWithDataInLocalBb(): Unit = runBlocking{
        class FakeService: TypeService {
            override suspend fun getAllTypes(): ContainerTypeResponse {
                return ContainerTypeResponse(
                    count = 1,
                    listOf(
                        TypeResponse(name = "fire", url = "fire/1/"),
                        TypeResponse(name = "ice", url = "ice/2/"),
                        TypeResponse(name = "ground", url = "ground/3/"),
                        TypeResponse(name = "poison", url = "poison/4/"),
                        TypeResponse(name = "steel", url = "steel/6/"),
                    )
                )
            }

            override suspend fun getType(typeId: Int): TypeDetailsResponse {
                val typeList = listOf(
                    TypeResponse(name = "fire", url = "fire/1"),
                    TypeResponse(name = "ice", url = "ice/2"),
                )

                return TypeDetailsResponse(
                    id = 1,
                    name ="fire",
                    damageRelationsResponse = DamageRelationsResponse(
                        doubleDamageFrom = typeList,
                        doubleDamageTo = emptyList(),
                        halfDamageFrom = typeList,
                        halfDamageTo = emptyList(),
                        noDamageFrom = typeList,
                        noDamageTo = emptyList()
                    )
                )
            }
        }

        class FakeDao: TypeDao {
            var typeList = listOf(
                TypeEntity(1, "fire"),
                TypeEntity(1, "ice"),
                TypeEntity(1, "steel"),
            )

            override suspend fun getAllTypes(): List<TypeEntity> {
                return typeList
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {

            }

            override suspend fun getDamageRelationByTypeId(typeId: Int): List<DamageRelationsEntity> {
                return listOf(
                    DamageRelationsEntity(
                        id = 1,
                        ownType = "ice",
                        ownTypeId = 1,
                        doubleDamageTo = null,
                        doubleDamageFrom = null,
                        halfDamageTo = null,
                        halfDamageFrom = null,
                        noDamageTo = null,
                        noDamageFrom = null
                    )
                )
            }

            override suspend fun insertDamageRelations(damageRelationsEntity: List<DamageRelationsEntity>) {

            }

            override suspend fun deleteDamageRelation(typeId: Int) {

            }
        }

        val service = FakeService()
        val dao = FakeDao()

        val repo = TypeRepositoryImp(
            service = service,
            dao = dao,
            makeNetworkCall = MakeNetworkCall()
        )

        val resourceFlow = repo.getAllTypesFlow().toList()
        val firstIsLoading = resourceFlow.first() as Resource.Loading
        val lastIsNotLoading = resourceFlow.last() as Resource.Loading
        val resourceSuccess = resourceFlow.filter { it is Resource.Success }
        val types = (resourceSuccess.first() as Resource.Success).data!!

        assertEquals(true, firstIsLoading.isLoading)
        assertEquals(1, resourceSuccess.count())
        assertEquals(dao.getAllTypes().count(), types.count())
        assertEquals(false, lastIsNotLoading.isLoading)
    }

    @Test
    fun getAllTypesFlowTestFailServiceWithNoDataLocalDb(): Unit = runBlocking{
        class FakeService: TypeService {
            override suspend fun getAllTypes(): ContainerTypeResponse {
                throw Exception("Error")
            }

            override suspend fun getType(typeId: Int): TypeDetailsResponse {
                throw Exception("Error")
            }
        }

        class FakeDao: TypeDao {
            var typeList = emptyList<TypeEntity>()

            override suspend fun getAllTypes(): List<TypeEntity> {
                return typeList
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {

            }

            override suspend fun getDamageRelationByTypeId(typeId: Int): List<DamageRelationsEntity> {
                return listOf(
                    DamageRelationsEntity(
                        id = 1,
                        ownType = "ice",
                        ownTypeId = 1,
                        doubleDamageTo = null,
                        doubleDamageFrom = null,
                        halfDamageTo = null,
                        halfDamageFrom = null,
                        noDamageTo = null,
                        noDamageFrom = null
                    )
                )
            }

            override suspend fun insertDamageRelations(damageRelationsEntity: List<DamageRelationsEntity>) {

            }

            override suspend fun deleteDamageRelation(typeId: Int) {

            }
        }

        val service = FakeService()
        val dao = FakeDao()

        val repo = TypeRepositoryImp(
            service = service,
            dao = dao,
            makeNetworkCall = MakeNetworkCall()
        )

        val resourceFlow = repo.getAllTypesFlow().toList()
        val firstIsLoading = resourceFlow.first() as Resource.Loading
        val lastIsNotLoading = resourceFlow.last() as Resource.Loading
        assertEquals(true, firstIsLoading.isLoading)
        assertEquals(false, lastIsNotLoading.isLoading)

        val resourceError = resourceFlow.filter { it is Resource.Error }

        assertEquals(1, resourceError.count())
    }

    @Test
    fun getAllTypesFlowTestFailServiceWithDataInLocalDb(): Unit = runBlocking{
        class FakeService: TypeService {
            override suspend fun getAllTypes(): ContainerTypeResponse {
                throw Exception("Error")
            }

            override suspend fun getType(typeId: Int): TypeDetailsResponse {
                throw Exception("Error")
            }
        }

        class FakeDao: TypeDao {
            var typeList = listOf(
                TypeEntity(1, "fire"),
                TypeEntity(1, "ice"),
                TypeEntity(1, "steel"),
            )

            override suspend fun getAllTypes(): List<TypeEntity> {
                return typeList
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {

            }

            override suspend fun getDamageRelationByTypeId(typeId: Int): List<DamageRelationsEntity> {
                return listOf(
                    DamageRelationsEntity(
                        id = 1,
                        ownType = "ice",
                        ownTypeId = 1,
                        doubleDamageTo = null,
                        doubleDamageFrom = null,
                        halfDamageTo = null,
                        halfDamageFrom = null,
                        noDamageTo = null,
                        noDamageFrom = null
                    )
                )
            }

            override suspend fun insertDamageRelations(damageRelationsEntity: List<DamageRelationsEntity>) {

            }

            override suspend fun deleteDamageRelation(typeId: Int) {

            }
        }

        val service = FakeService()
        val dao = FakeDao()

        val repo = TypeRepositoryImp(
            service = service,
            dao = dao,
            makeNetworkCall = MakeNetworkCall()
        )

        val resourceFlow = repo.getAllTypesFlow().toList()
        val firstIsLoading = resourceFlow.first() as Resource.Loading
        val lastIsNotLoading = resourceFlow.last() as Resource.Loading
        assertEquals(true, firstIsLoading.isLoading)
        assertEquals(false, lastIsNotLoading.isLoading)

        val resources = resourceFlow.filter { it is Resource.Success }
        assertEquals(1, resources.count())

        val types = (resources.first() as Resource.Success).data!!
        assertEquals(dao.getAllTypes().count(), types.count())
    }
}