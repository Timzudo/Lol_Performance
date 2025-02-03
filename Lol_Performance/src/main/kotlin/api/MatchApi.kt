package org.example.api

import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch
import org.example.ApiService
import org.example.util.match.LOLMatchResponse

class MatchApi {

    companion object{
        suspend fun fetchMatches(apiService: ApiService, puuid: String, apiKey: String, count: Int? = null, start: Int?= null): List<String>? {
            return try {
                val response = apiService.getMatches(puuid, apiKey, count, start)
                response
            } catch (e: Exception) {
                println("Error: ${e.message}")
                null
            }
        }

        suspend fun fetchMatch(apiService: ApiService, matchId: String, apiKey: String): org.example.util.match.LOLMatchResponse? {
            return try {
                val response = apiService.getMatch(matchId, apiKey)
                response
            } catch (e: Exception) {
                println("Error: ${e.message}")
                null
            }
        }
    }
}