package com.priorDev.pokerroutejc.data.network.pkType

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.database.TypeDao
import com.priorDev.pokerroutejc.data.database.toDB
import com.priorDev.pokerroutejc.data.network.NetworkError
import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.featureTypes.domain.TypeData
import com.priorDev.pokerroutejc.featureTypes.domain.TypeDetailsData
import com.priorDev.pokerroutejc.featureTypes.domain.TypeRepository
import com.priorDev.pokerroutejc.featureTypes.domain.toDomain
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.spinningWheelOrRefresh
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

            emit(getAllTypes(isRefresh))

            emit(ResourceFlow.Loading(loadingIndicator = LoadingIndicator.None))
        }
    }

    override suspend fun getAllTypes(isRefresh: Boolean): ResourceFlow<List<TypeData>> {
        // Single source of truth DATABASE
        val networkResult = service.getAllTypes()
        if (networkResult is NetworkResource.Success) {
            networkResult.data.types.let { typeList ->
                dao.insertTypes(typeList.map { it.toDB() })
            }
        }

        val typesEntity = dao.getAllTypes()
        val typeList = typesEntity.map { it.toDomain() }

        return when {
            networkResult is NetworkResource.Fail && isRefresh -> {
                ResourceFlow.Error(networkErrorType = networkResult.error)
            }

            typeList.isNotEmpty() -> {
                ResourceFlow.Success(typeList)
            }

            networkResult is NetworkResource.Fail -> {
                ResourceFlow.Error(networkErrorType = networkResult.error)
            }

            else -> {
                ResourceFlow.Error(networkErrorType = NetworkError.EmptyContent)
            }
        }
    }

    override suspend fun getTypeFlow(typeId: Int): Flow<ResourceFlow<TypeDetailsData>> {
        // Single source of truth DATABASE
        return flow {
            emit(ResourceFlow.Loading())

            val networkResource = service.getType(typeId)

            if (networkResource is NetworkResource.Success) {
                dao.deleteDamageRelation(networkResource.data.id)
                dao.insertDamageRelations(networkResource.data.toDB())
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
                ResourceFlow.Success(networkResource.data.toDomain())
            }
        }
    }
}
