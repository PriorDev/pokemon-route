package com.priorDev.pokerroutejc.data.network

import android.util.Log
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class MakeKtorNetworkCall @Inject constructor(
    private val dispatcher: CoroutineDispatcher
): INetworkCaller {
    override suspend operator fun invoke(
        call: suspend () -> HttpResponse
    ): NetworkResource {
        return withContext(dispatcher) {
            try {
                val response = call.invoke()

                when (response.status.value) {
                    in 200..299 -> {
                        NetworkResource.Success(response)
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
