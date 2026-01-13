package com.priorDev.pokerroutejc.core.data.network

import com.priorDev.pokerroutejc.core.data.network.utils.NetworkRequestData
import com.priorDev.pokerroutejc.core.data.network.utils.NetworkResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class NetworkServiceKtor(
    private val client: HttpClient,
    private val caller: NetworkCaller,
) : NetworkService {
    override suspend fun <T> get(
        requestData: NetworkRequestData
    ): NetworkResource<T> {
        return caller(requestData.typeInfo) {
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
