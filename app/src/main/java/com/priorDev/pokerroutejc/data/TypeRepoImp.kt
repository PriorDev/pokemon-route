package com.priorDev.pokerroutejc.data

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.database.TypeDao
import com.priorDev.pokerroutejc.data.database.toDB
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.data.network.utils.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.TypeNetService
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData
import com.priorDev.pokerroutejc.domain.types.models.toDomain
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.spinningWheelOrRefresh
import com.priorDev.pokerroutejc.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TypeRepoImp(
    private val service: TypeNetService,
    private val dao: TypeDao,
) : TypeRepo {
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

    override suspend fun getType(typeId: Int): Resource<TypeDetailsData> {
        return when (val networkResource = service.getType(typeId)) {
            is NetworkResource.Fail -> {
                Resource.Error(networkErrorType = NetworkError.EmptyContent)
            }

            is NetworkResource.Success -> {
                Resource.Success(networkResource.data.toDomain())
            }
        }
    }
}
