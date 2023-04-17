package com.prior_dev.pokemonrroutejc.feature_types.data

import android.util.Log
import com.prior_dev.pokemonrroutejc.core.EnumTags
import com.prior_dev.pokemonrroutejc.core.Resource
import com.prior_dev.pokemonrroutejc.core.components.SealedMyExceptions
import com.prior_dev.pokemonrroutejc.feature_types.data.database.DamageRelationsEntity
import com.prior_dev.pokemonrroutejc.feature_types.data.database.TypeDao
import com.prior_dev.pokemonrroutejc.feature_types.data.database.toDB
import com.prior_dev.pokemonrroutejc.feature_types.data.network.TypeService
import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.TypeDetailsResponse
import com.prior_dev.pokemonrroutejc.feature_types.domain.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TypeRepositoryImp @Inject constructor(
    private val service: TypeService,
    private val dao: TypeDao,
): TypeRepository {
    override suspend fun getAllTypes(): Flow<Resource<List<TypeData>>>{
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

    override suspend fun getType(typeId: Int): Flow<Resource<TypeDetailsData>>{
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

                } ?: emit(Resource.Error("Error al obtener la información del servidor"))

            }catch (ex: Exception){
                Log.e(EnumTags.Error.tag, "getType: ${ex.message}")

                if(damageRelationFromDB.isEmpty()){
                    emit(Resource.Error(SealedMyExceptions.serverError))
                }
            }

            emit(Resource.Loading(false))
        }
    }
}