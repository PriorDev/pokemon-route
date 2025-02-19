package com.priorDev.pokerroutejc.data.network.pkType

import com.priorDev.pokerroutejc.data.network.MakeRetrofitNetworkCall
import com.priorDev.pokerroutejc.core.Resource
import com.priorDev.pokerroutejc.data.database.TypeDao
import com.priorDev.pokerroutejc.data.database.toDB
import com.priorDev.pokerroutejc.data.network.NetworkError
import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.featureTypes.domain.TypeData
import com.priorDev.pokerroutejc.featureTypes.domain.TypeDetailsData
import com.priorDev.pokerroutejc.featureTypes.domain.TypeRepository
import com.priorDev.pokerroutejc.featureTypes.domain.toDomain
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TypeRepositoryImp @Inject constructor(
    private val service: ITypeService,
    private val dao: TypeDao,
    private val makeRetrofitNetworkCall: MakeRetrofitNetworkCall
) : TypeRepository {
    override suspend fun getAllTypesFlow(): Flow<Resource<List<TypeData>>> {
        // Single source of truth DATABASE
        return flow {
            emit(Resource.Loading())

            emit(getAllTypes())

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTypeFlow(typeId: Int): Flow<Resource<TypeDetailsData>> {
        // Single source of truth DATABASE
        return flow {
            emit(Resource.Loading())

            val response = makeRetrofitNetworkCall {
                service.getType(typeId)
            }

            if (response is Resource.Success) {
                response.data?.let { typeDetails ->
                    dao.deleteDamageRelation(typeDetails.id)
                    dao.insertDamageRelations(typeDetails.toDB())
                }
            }

            val damageRelationEntity = dao.getDamageRelationByTypeId(typeId = typeId)

            if (damageRelationEntity.isEmpty()) {
                val error = (response as Resource.Error).uiMessages
                emit(Resource.Error(uiMessages = error, throwable = response.throwable))
            } else {
                emit(Resource.Success(damageRelationEntity.toDomain()))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getAllTypes(): Resource<List<TypeData>> {
        val networkResult = service.getAllTypes()

        if (networkResult is NetworkResource.Success) {
            val container: ContainerTypeResponse = networkResult.response.body()

            container.types.let { typeList ->
                dao.insertTypes(typeList.map { it.toDB() })
            }
        }

        val typesEntity = dao.getAllTypes()
        val typeList = typesEntity.map { it.toDomain() }

        return if (typeList.isEmpty()) {
            Resource.Error(networkErrorType = NetworkError.EmptyContent)
        } else {
            Resource.Success(typeList)
        }
    }

    override suspend fun getType(typeId: Int): Resource<TypeDetailsData> {
        val response = makeRetrofitNetworkCall {
            service.getType(typeId)
        }

        if (response is Resource.Success) {
            response.data?.let {
                return Resource.Success(it.toDomain())
            }
        }

        val error = (response as Resource.Error).uiMessages
        return Resource.Error(uiMessages = error, throwable = response.throwable)
    }
}
