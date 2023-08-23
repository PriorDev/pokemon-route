package com.prior_dev.pokerroutejc.repositories.type_repo

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

class TypeRepoGetTypeTest {
    @Test
    fun getTypeSuccess(): Unit = runBlocking {
        class FakeService: TypeService {
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

        class FakeDao: TypeDao {
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

        val repo = TypeRepositoryImp(service, dao)

        val resource = repo.getType(1)
        assert(resource is Resource.Success)

        val resourceSuccess = resource as Resource.Success
        assertEquals(1, resourceSuccess.data!!.id)
    }

    @Test
    fun getTypeFail(): Unit = runBlocking {
        class FakeService: TypeService {
            override suspend fun getAllTypes(): ContainerTypeResponse? {
                return null
            }

            override suspend fun getType(typeId: Int): TypeDetailsResponse {
                throw Exception("Error")
            }
        }
        val service = FakeService()

        class FakeDao: TypeDao {
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

        val repo = TypeRepositoryImp(service, dao)

        val resource = repo.getType(1)
        assert(resource is Resource.Error)
    }
}