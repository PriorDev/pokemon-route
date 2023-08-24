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
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class TypeRepoGetAllTypesTest {
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
            var insert = false
            override suspend fun getAllTypes(): List<TypeEntity> {
                return if(insert){
                    listOf(
                        TypeEntity(1, "ice"),
                        TypeEntity(2, "fire"),
                        TypeEntity(3, "poison"),
                    )
                }else{
                    emptyList()
                }
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {
                insert = true
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

        val resource = repo.getAllTypes()

        assertEquals(true, dao.insert)
        assert(resource is Resource.Success)
        val resourceSuccess = resource as Resource.Success

        assertEquals(3, resourceSuccess.data!!.count())
    }

    @Test
    fun getAllTypesFlowTestSuccessWithDataInDb(): Unit = runBlocking{
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

            override suspend fun getType(typeId: Int): TypeDetailsResponse? {
                return null
            }
        }

        class FakeDao: TypeDao {
            var insert = false
            override suspend fun getAllTypes(): List<TypeEntity> {
                return listOf(
                    TypeEntity(1, "fire"),
                    TypeEntity(1, "ice"),
                    TypeEntity(1, "steel"),
                )
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {
                insert = true
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

        val resource = repo.getAllTypes()

        assertEquals(true, dao.insert)
        assert(resource is Resource.Success)
        val resourceSuccess = resource as Resource.Success

        assertEquals(3, resourceSuccess.data!!.count())
    }

    @Test
    fun getAllTypesFlowTestFailWithNoDataInDb(): Unit = runBlocking{
        class FakeService: TypeService {
            override suspend fun getAllTypes(): ContainerTypeResponse {
                throw Exception("Error")
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
            var insert = false
            override suspend fun getAllTypes(): List<TypeEntity> {
                return emptyList()
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {
                insert = true
            }

            override suspend fun getDamageRelationByTypeId(typeId: Int): List<DamageRelationsEntity> {
                return emptyList()
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

        val resource = repo.getAllTypes()

        assertEquals(false, dao.insert)
        assert(resource is Resource.Error)
    }

    @Test
    fun getAllTypesFlowTestFailWithDataInDb(): Unit = runBlocking{
        class FakeService: TypeService {
            override suspend fun getAllTypes(): ContainerTypeResponse {
                throw Exception("Error")
            }

            override suspend fun getType(typeId: Int): TypeDetailsResponse? {
                return null
            }
        }

        class FakeDao: TypeDao {
            var insert = false
            override suspend fun getAllTypes(): List<TypeEntity> {
                return listOf(
                    TypeEntity(1, "fire"),
                    TypeEntity(1, "ice"),
                    TypeEntity(1, "steel"),
                )
            }

            override suspend fun insertTypes(types: List<TypeEntity>) {
                insert = true
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

        val resource = repo.getAllTypes()

        assertEquals(false, dao.insert)
        assert(resource is Resource.Success)
        val resourceSuccess = resource as Resource.Success

        assertEquals(3, resourceSuccess.data!!.count())
    }
}