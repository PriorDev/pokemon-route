package com.priorDev.pokerroutejc.data.network

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.reflect.TypeInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class MakeKtorNetworkCall @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) : INetworkCaller {
    override suspend operator fun <T> invoke(
        typeInfo: TypeInfo,
        call: suspend () -> HttpResponse
    ): NetworkResource<T> {
        return withContext(dispatcher) {
            try {
                val response = call.invoke()

                when (response.status.value) {
                    in 200..299 -> {
                        val body = response.body<T>(typeInfo)
                        NetworkResource.Success(body)
                    }

                    in 400..499 -> {
                        NetworkResource.Fail(NetworkError.ClientError())
                    }

                    in 500..599 -> {
                        NetworkResource.Fail(
                            NetworkError.ServerError(
                                serverMessage = "Imagine that server has a custom message" +
                                    " that we can show to the user"
                            )
                        )
                    }

                    else -> {
                        NetworkResource.Fail(NetworkError.UnknownError)
                    }
                }
            } catch (e: Exception) {
                Log.e("MakeKtorNetworkCall", e.message.orEmpty())
                NetworkResource.Fail(NetworkError.UnableToConnect)
            }
        }
    }
}
