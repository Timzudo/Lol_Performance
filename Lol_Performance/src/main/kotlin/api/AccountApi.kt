package org.example.api

import no.stelar7.api.r4j.pojo.shared.RiotAccount
import org.example.ApiService

class AccountApi {

    companion object{
        suspend fun fetchPUUID(apiService: ApiService, username: String, tag: String, apiKey: String): RiotAccount? {
            return try {
                val response = apiService.getPUUID(username, tag, apiKey)
                response
            } catch (e: Exception) {
                println("Error: ${e.message}")
                null
            }
        }
    }
}