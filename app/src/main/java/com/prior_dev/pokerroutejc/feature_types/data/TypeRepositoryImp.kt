package com.prior_dev.pokerroutejc.feature_types.data

import com.prior_dev.pokerroutejc.core.MakeNetworkCall
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_types.data.database.TypeDao
import com.prior_dev.pokerroutejc.feature_types.data.database.toDB
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData
import com.prior_dev.pokerroutejc.feature_types.domain.TypeDetailsData
import com.prior_dev.pokerroutejc.feature_types.domain.TypeRepository
import com.prior_dev.pokerroutejc.feature_types.domain.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TypeRepositoryImp @Inject constructor(
    private val service: TypeService,
    private val dao: TypeDao,
    private val makeNetworkCall: MakeNetworkCall
): TypeRepository {
    override suspend fun getAllTypesFlow(): Flow<Resource<List<TypeData>>>{
        //Single source of truth DATABASE
        return flow { 
            emit(Resource.Loading())

            val response = makeNetworkCall{
                service.getAllTypes()
            }

            if(response is Resource.Success){
                response.data?.types?.let { typeList ->
                    dao.insertTypes(typeList.map { it.toDB() })
                }
            }

            val typesEntity = dao.getAllTypes()
            val typeList = typesEntity.map { it.toDomain() }

            if(typeList.isEmpty()){
                val error = (response as Resource.Error).uiMessages
                emit(Resource.Error(error))
            }else{
                emit(Resource.Success(typeList))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTypeFlow(typeId: Int): Flow<Resource<TypeDetailsData>>{
        //Single source of truth DATABASE
        return flow {
            emit(Resource.Loading())

            val response = makeNetworkCall{
                service.getType(typeId)
            }

            if(response is Resource.Success){
                response.data?.let { typeDetails ->
                    dao.deleteDamageRelation(typeDetails.id)
                    dao.insertDamageRelations(typeDetails.toDB())
                }
            }

            val damageRelationEntity = dao.getDamageRelationByTypeId(typeId = typeId)

            if(damageRelationEntity.isEmpty()){
                val error = (response as Resource.Error).uiMessages
                emit(Resource.Error(error))
            }else{
                emit(Resource.Success(damageRelationEntity.toDomain()))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getAllTypes(): Resource<List<TypeData>> {
        val response = makeNetworkCall {
            service.getAllTypes()
        }

        if(response is Resource.Success){
            response.data?.let { typesContainer ->
                dao.insertTypes(typesContainer.types.map { it.toDB() })
            }
        }

        val typesEntity = dao.getAllTypes()

        if(typesEntity.isEmpty()){
            val error = (response as Resource.Error).uiMessages
            return Resource.Error(error)
        }

        val typesList = typesEntity.map { it.toDomain() }
        return Resource.Success(typesList)
    }

    override suspend fun getType(typeId: Int): Resource<TypeDetailsData> {
        val response = makeNetworkCall{
            service.getType(typeId)
        }

        if(response is Resource.Success){
            response.data?.let {
                return Resource.Success(it.toDomain())
            }
        }

        val error = (response as Resource.Error).uiMessages
        return Resource.Error(error)
    }
}







