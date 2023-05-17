package com.prior_dev.pokerroutejc.feature_types.data

import android.util.Log
import com.prior_dev.pokerroutejc.core.EnumTags
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.core.components.SealedMyExceptions
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
): TypeRepository {
    override suspend fun getAllTypesFlow(): Flow<Resource<List<TypeData>>>{
        return flow { 
            emit(Resource.Loading())
            
            val typeEntity = dao.getAllTypes()

            if(typeEntity.isNotEmpty()){
                emit(Resource.Success(
                    typeEntity.map { it.toDomain() }
                ))
                emit(Resource.Loading(false))
                return@flow
            }

            try{
                val typeResponse = service.getAllTypes()
                typeResponse?.let { response ->
                    dao.insertTypes(
                        response.types.map { it.toDB() }
                    )
                }

                emit(Resource.Success(
                    dao.getAllTypes().map { it.toDomain() }
                ))
            }catch (e: Exception){
                Log.e(EnumTags.Error.tag, "getAllTypes: ${e.message}")
                emit(Resource.Error(SealedMyExceptions.serverError))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTypeFlow(typeId: Int): Flow<Resource<TypeDetailsData>>{
        return flow {
            emit(Resource.Loading())

            val damageRelationFromDB = dao.getDamageRelationByTypeId(typeId = typeId)

            if(damageRelationFromDB.isNotEmpty()){
                emit(Resource.Success(damageRelationFromDB.toDomain()))
                emit(Resource.Loading(false))
            }

            try {
                val response = service.getType(typeId)

                response?.let { typeDetails ->
                    //Elimina datos anteriores
                    dao.deleteDamageRelation(typeDetails.id)
                    dao.insertDamageRelations(typeDetails.toDB())

                    emit(Resource.Loading())
                    emit(Resource.Success(typeDetails.toDomain()))

                } ?: emit(Resource.Error(SealedMyExceptions.serverError))

            }catch (ex: Exception){
                Log.e(EnumTags.Error.tag, "getType: ${ex.message}")

                if(damageRelationFromDB.isEmpty()){
                    emit(Resource.Error(SealedMyExceptions.serverError))
                }
            }

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







