package com.priorDev.pokerroutejc.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import javax.inject.Inject

class KtorNetworkServiceImp @Inject constructor(
    private val client: HttpClient,
    private val makeKtorNetworkCall: INetworkCaller,
) : INetWorkService {
    override suspend fun <T> get(
        requestData: NetworkRequestData
    ): NetworkResource<T> {
        return makeKtorNetworkCall(requestData.typeInfo) {
            client.get(requestData.url) {
                url {
                    // Add segments to the URL
                    requestData.segments.forEach {
                        appendPathSegments(it)
                    }

                    // Add params to the URL
                    requestData.params.forEach { (key, value) ->
                        parameters.append(key, value)
                    }
                }
            }
        }
    }
}
