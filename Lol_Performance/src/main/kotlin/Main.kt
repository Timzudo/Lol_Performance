package org.example

import kotlinx.coroutines.runBlocking
import no.stelar7.api.r4j.basic.APICredentials
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard
import no.stelar7.api.r4j.basic.utils.LazyList
import no.stelar7.api.r4j.impl.R4J
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch
import no.stelar7.api.r4j.pojo.shared.RiotAccount
import org.example.api.AccountApi
import org.example.api.MatchApi
import org.example.mainRunners.GetLastGameOverview
import org.example.mainRunners.GetPlayerGamesPerformance
import org.example.performance.PerformanceEvaluator
import org.example.performance.PerformanceRunner
import org.example.util.match.LOLMatchResponse
import org.example.util.match.round
import retrofit2.Call
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.CompletableFuture



fun main() {
    val apiKey = "RGAPI-4f2f72ab-e058-48a7-a4e5-14fb23fbfae3"
    val apiService = RetrofitClient.instance


    var running = true

    while(running){
        println("Choose an option:")
        println("-----------------")
        println("1. Get game overview")
        println("2. Get player games performance")
        println("3. Exit")
        println("-----------------")
        val option = readlnOrNull()

        when(option){
            "1" -> GetLastGameOverview().getLastGameOverview(apiKey, apiService)
            "2" -> GetPlayerGamesPerformance().getPlayerGamesPerformance(apiKey, apiService)
            "3" -> running = false
            else -> println("Bro...")
        }
    }


}





