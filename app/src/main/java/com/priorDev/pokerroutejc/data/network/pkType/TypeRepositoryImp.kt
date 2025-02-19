package com.priorDev.pokerroutejc.data.network.pkType

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.database.TypeDao
import com.priorDev.pokerroutejc.data.database.toDB
import com.priorDev.pokerroutejc.data.network.NetworkError
import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.response.ContainerTypeResponse
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeDetailsResponse
import com.priorDev.pokerroutejc.featureTypes.domain.TypeData
import com.priorDev.pokerroutejc.featureTypes.domain.TypeDetailsData
import com.priorDev.pokerroutejc.featureTypes.domain.TypeRepository
import com.priorDev.pokerroutejc.featureTypes.domain.toDomain
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.spinningWheelOrRefresh
import io.ktor.client.call.body
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TypeRepositoryImp @Inject constructor(
    private val service: ITypeService,
    private val dao: TypeDao,
) : TypeRepository {
    override suspend fun getAllTypesFlow(isRefresh: Boolean): Flow<ResourceFlow<List<TypeData>>> {
        return flow {

            emit(
                ResourceFlow.Loading(loadingIndicator = spinningWheelOrRefresh(isRefresh))
            )

            emit(getAllTypes())

            emit(ResourceFlow.Loading(loadingIndicator = LoadingIndicator.None))
        }
    }

    override suspend fun getAllTypes(): ResourceFlow<List<TypeData>> {
        // Single source of truth DATABASE
        when(val networkResult = service.getAllTypes()) {
            is NetworkResource.Fail -> {
                return ResourceFlow.Error(networkErrorType = networkResult.error)
            }
            is NetworkResource.Success -> {
                val container: ContainerTypeResponse = networkResult.response.body()

                container.types.let { typeList ->
                    dao.insertTypes(typeList.map { it.toDB() })
                }
            }
        }

        val typesEntity = dao.getAllTypes()
        val typeList = typesEntity.map { it.toDomain() }

        delay(10000)
        return if (typeList.isEmpty()) {
            ResourceFlow.Error(networkErrorType = NetworkError.EmptyContent)
        } else {
            ResourceFlow.Success(typeList)
        }
    }

    override suspend fun getTypeFlow(typeId: Int): Flow<ResourceFlow<TypeDetailsData>> {
        // Single source of truth DATABASE
        return flow {
            emit(ResourceFlow.Loading())

            val networkResource = service.getType(typeId)

            if (networkResource is NetworkResource.Success) {
                val typeDetails: TypeDetailsResponse = networkResource.response.body()

                dao.deleteDamageRelation(typeDetails.id)
                dao.insertDamageRelations(typeDetails.toDB())
            }

            val damageRelationEntity = dao.getDamageRelationByTypeId(typeId = typeId)

            if (damageRelationEntity.isEmpty()) {
                emit(ResourceFlow.Error(networkErrorType = NetworkError.EmptyContent))
            } else {
                emit(ResourceFlow.Success(damageRelationEntity.toDomain()))
            }

            emit(ResourceFlow.Loading(false))
        }
    }

    override suspend fun getType(typeId: Int): ResourceFlow<TypeDetailsData> {
        return when (val networkResource = service.getType(typeId)) {
            is NetworkResource.Fail -> {
                ResourceFlow.Error(networkErrorType = NetworkError.EmptyContent)
            }

            is NetworkResource.Success -> {
                val typeDetails: TypeDetailsResponse = networkResource.response.body()
                ResourceFlow.Success(typeDetails.toDomain())
            }
        }
    }
}
