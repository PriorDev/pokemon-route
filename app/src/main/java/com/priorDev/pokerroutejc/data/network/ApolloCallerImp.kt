package com.priorDev.pokerroutejc.data.network

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Query
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.data.network.utils.NetworkResource

@Suppress("TooGenericExceptionCaught")
class ApolloCallerImp(
    private val apolloClient: ApolloClient
) : ApolloCaller {
    override suspend operator fun <T : Query.Data> invoke(
        query: Query<T>
    ): NetworkResource<T> {
        return try {
            val response = apolloClient
                .query(query)
                .execute()

            when {
                response.hasErrors() -> {
                    response.errors.orEmpty().forEach {
                        Log.e("ApolloCaller", it.message)
                    }

                    NetworkResource.Fail(
                        response.errors?.firstOrNull()?.message?.let { errorMessage ->
                            NetworkError.ServerError(errorMessage)
                        } ?: NetworkError.UnknownError
                    )
                }

                response.data == null -> {
                    NetworkResource.Fail(NetworkError.EmptyContent)
                }

                response.data != null -> {
                    NetworkResource.Success(response.data!!)
                }

                else -> {
                    NetworkResource.Fail(NetworkError.UnknownError)
                }
            }
        } catch (e: Exception) {
            Log.e("ApolloCaller", e.message.orEmpty())
            NetworkResource.Fail(NetworkError.UnableToConnect, e)
        }
    }
}
