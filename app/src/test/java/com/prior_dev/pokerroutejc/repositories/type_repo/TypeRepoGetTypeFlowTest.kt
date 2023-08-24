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

class TypeRepoGetTypeFlowTest {
    @Test
    fun getTypeSuccessWithNoDataInDb(): Unit = runBlocking {
        class FakeService: TypeService{
            override suspend fun getAllTypes(): ContainerTypeResponse? {
                return null
            }

            override suspend fun getType(typeId: Int): TypeDetailsResponse {
                val typeList = listOf(
                    TypeResponse(name = "fire", url = "fire/1/"),
                    TypeResponse(name = "ice", url = "ice/2/"),
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

        val service = FakeService()

        class FakeDao: TypeDao{
            var insert = false
            override suspend fun getAllTypes(): List<TypeEntity> {
                TODO("Not yet implemented")
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {
                TODO("Not yet implemented")
            }

            override suspend fun getDamageRelationByTypeId(typeId: Int): List<DamageRelationsEntity> {
                return if(insert)
                {
                    listOf(
                        DamageRelationsEntity(ownTypeId = 1, ownType = "")
                    )
                }else{
                    emptyList()
                }

            }

            override suspend fun insertDamageRelations(damageRelationsEntity: List<DamageRelationsEntity>) {
                insert = true
            }

            override suspend fun deleteDamageRelation(typeId: Int) {
                insert = false
            }

        }

        val dao = FakeDao()

        val repo = TypeRepositoryImp(service, dao, MakeNetworkCall())

        val resourceFlow = repo.getTypeFlow(1).toList()

        val firstIsLoading = resourceFlow.first() as Resource.Loading
        val lastIsNotLoading = resourceFlow.last() as Resource.Loading
        assertEquals(true, firstIsLoading.isLoading)
        assertEquals(false, lastIsNotLoading.isLoading)

        val resourceSucess = resourceFlow.filter { it is Resource.Success }
        assertEquals(1, resourceSucess.count())
        assertEquals(true, dao.insert)

        //TODO:
//        val damageFroBb = dao.getDamageRelationByTypeId(1)
//        assertEquals(resourceList.first().data, damageFroBb.toDomain())
    }

    @Test
    fun getTypeSuccessWithDataInDb(): Unit = runBlocking {
        class FakeService: TypeService{
            override suspend fun getAllTypes(): ContainerTypeResponse? {
                return null
            }

            override suspend fun getType(typeId: Int): TypeDetailsResponse {
                val typeList = listOf(
                    TypeResponse(name = "fire", url = "fire/1/"),
                    TypeResponse(name = "ice", url = "ice/2/"),
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

        val service = FakeService()

        class FakeDao: TypeDao{
            var insert = false
            override suspend fun getAllTypes(): List<TypeEntity> {
                TODO("Not yet implemented")
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {
                TODO("Not yet implemented")
            }

            override suspend fun getDamageRelationByTypeId(typeId: Int): List<DamageRelationsEntity> {
                return listOf(
                    DamageRelationsEntity(
                        id = 1,
                        ownTypeId = 1,
                        ownType = "fire",
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
                insert = true
            }

            override suspend fun deleteDamageRelation(typeId: Int) {
                insert = false
            }

        }

        val dao = FakeDao()

        val repo = TypeRepositoryImp(service, dao, MakeNetworkCall())

        val resourceFlow = repo.getTypeFlow(1).toList()

        val firstIsLoading = resourceFlow.first() as Resource.Loading
        val lastIsNotLoading = resourceFlow.last() as Resource.Loading
        assertEquals(true, firstIsLoading.isLoading)
        assertEquals(false, lastIsNotLoading.isLoading)

        val resourceSuccess = resourceFlow.filter { it is Resource.Success }
        assertEquals(1, resourceSuccess.count())
        //TODO:
        //assertEquals(true, dao.insert)

        //TODO:
//        val damageFroBb = dao.getDamageRelationByTypeId(1)
//        assertEquals(resourceList.first().data, damageFroBb.toDomain())
    }

    @Test
    fun getTypeFailWithNoDataInDb(): Unit = runBlocking {
        class FakeService: TypeService{
            override suspend fun getAllTypes(): ContainerTypeResponse? {
                return null
            }

            override suspend fun getType(typeId: Int): TypeDetailsResponse {
                throw Exception("Error")
            }
        }

        val service = FakeService()

        class FakeDao: TypeDao{
            var insert = false
            override suspend fun getAllTypes(): List<TypeEntity> {
                TODO("Not yet implemented")
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {
                TODO("Not yet implemented")
            }

            override suspend fun getDamageRelationByTypeId(typeId: Int): List<DamageRelationsEntity> {
                return emptyList()
            }

            override suspend fun insertDamageRelations(damageRelationsEntity: List<DamageRelationsEntity>) {
                insert = true
            }

            override suspend fun deleteDamageRelation(typeId: Int) {
                insert = false
            }

        }

        val dao = FakeDao()

        val repo = TypeRepositoryImp(service, dao, MakeNetworkCall())

        val resourceFlow = repo.getTypeFlow(1).toList()

        val firstIsLoading = resourceFlow.first() as Resource.Loading
        val lastIsNotLoading = resourceFlow.last() as Resource.Loading
        assertEquals(true, firstIsLoading.isLoading)
        assertEquals(false, lastIsNotLoading.isLoading)

        val resourceError = resourceFlow.filter { it is Resource.Error }
        assertEquals(1, resourceError.count())
        assertEquals(false, dao.insert)
    }

    @Test
    fun getTypeFailWithDataInDb(): Unit = runBlocking {
        class FakeService: TypeService{
            override suspend fun getAllTypes(): ContainerTypeResponse? {
                return null
            }

            override suspend fun getType(typeId: Int): TypeDetailsResponse {
                throw Exception("Error")
            }
        }

        val service = FakeService()

        class FakeDao: TypeDao{
            var insert = false
            override suspend fun getAllTypes(): List<TypeEntity> {
                TODO("Not yet implemented")
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {
                TODO("Not yet implemented")
            }

            override suspend fun getDamageRelationByTypeId(typeId: Int): List<DamageRelationsEntity> {
                return listOf(
                    DamageRelationsEntity(
                        id = 1,
                        ownTypeId = 1,
                        ownType = "fire",
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
                insert = true
            }

            override suspend fun deleteDamageRelation(typeId: Int) {
                insert = false
            }

        }

        val dao = FakeDao()

        val repo = TypeRepositoryImp(service, dao, MakeNetworkCall())

        val resourceFlow = repo.getTypeFlow(1).toList()

        val firstIsLoading = resourceFlow.first() as Resource.Loading
        val lastIsNotLoading = resourceFlow.last() as Resource.Loading
        assertEquals(true, firstIsLoading.isLoading)
        assertEquals(false, lastIsNotLoading.isLoading)

        val resourceSuccess = resourceFlow.filter { it is Resource.Success }
        assertEquals(1, resourceSuccess.count())
        assertEquals(false, dao.insert)

        //TODO:
//        val damageFroBb = dao.getDamageRelationByTypeId(1)
//        assertEquals(resourceList.first().data, damageFroBb.toDomain())
    }

}