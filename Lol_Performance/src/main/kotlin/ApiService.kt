package org.example

import com.sun.tools.javac.comp.Todo
import no.stelar7.api.r4j.basic.utils.LazyList
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch
import no.stelar7.api.r4j.pojo.shared.RiotAccount
import org.example.util.match.LOLMatchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
    suspend fun getPUUID(
        @Path("gameName") gameName: String,
        @Path("tagLine") tagLine: String,
        @Query("api_key") apiKey: String
    ): RiotAccount


    @GET("lol/match/v5/matches/by-puuid/{puuid}/ids")
    suspend fun getMatches(
        @Path("puuid") puuid: String,
        @Query("api_key") apiKey: String,
        @Query("count") count: Int? = null,
        @Query("start") start: Int? = null
    ): List<String>


    @GET("lol/match/v5/matches/{matchId}")
    suspend fun getMatch(
        @Path("matchId") matchId: String,
        @Query("api_key") apiKey: String
    ): org.example.util.match.LOLMatchResponse
}
