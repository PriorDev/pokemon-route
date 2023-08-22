package com.prior_dev.pokerroutejc.feature_types.data

import android.util.Log
import com.prior_dev.pokerroutejc.core.EnumTags
import com.prior_dev.pokerroutejc.core.MakeNetworkCall
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_types.data.database.TypeDao
import com.prior_dev.pokerroutejc.feature_types.data.database.toDB
import com.prior_dev.pokerroutejc.feature_types.data.network.TypeService
import com.prior_dev.pokerroutejc.feature_types.domain.*
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
            }

            emit(Resource.Success(typeList))

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
            }

            emit(Resource.Success(damageRelationEntity.toDomain()))

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getAllTypes(): Resource<List<TypeData>> {
        val typesEntity = dao.getAllTypes()

        if(typesEntity.isNotEmpty()){
            return Resource.Success(
                typesEntity.map { it.toDomain() }
            )
        }

        return try{
            val typeResponse = service.getAllTypes()
            typeResponse?.let { response ->
                dao.insertTypes(
                    response.types.map { it.toDB() }
                )
                Resource.Success(
                    typeResponse.types.map { it.toDomain() }
                )
            } ?: Resource.Error(SealedMyExceptions.serverError)
        }catch (e: Exception){
            Log.e(EnumTags.Error.tag, "getAllTypes: ${e.message}")
            Resource.Error(SealedMyExceptions.serverError)
        }
    }

    override suspend fun getType(typeId: Int): Resource<TypeDetailsData> {
        return try {
            val response = service.getType(typeId)

            response?.let { typeDetails ->
                Resource.Success(typeDetails.toDomain())
            } ?: Resource.Error(SealedMyExceptions.serverError)

        }catch (ex: Exception){
            Log.e(EnumTags.Error.tag, "getType: ${ex.message}")
            return Resource.Error(SealedMyExceptions.serverError)
        }
    }
}







