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
   val apiKey = "RGAPI-88c2d650-ea0b-4494-b2f5-2b0311720cfd"
   val apiService = RetrofitClient.instance


   println("Insert name and tag")
   val nameAndTag = readlnOrNull()
   println("Insert number of games")
   val numberOfGames = readlnOrNull()

   val values = nameAndTag?.split("#")

   val account: RiotAccount? = runBlocking {
      values?.let { AccountApi.fetchPUUID(apiService, it[0], it[1], apiKey) }
   }

   val matchIDList: List<String>? = runBlocking {
      account?.let { MatchApi.fetchMatches(apiService, account.puuid, apiKey) }
   }

   val matchList: MutableList<LOLMatchResponse> = mutableListOf()

   if (numberOfGames != null) {
      for (i in 0 until numberOfGames.toInt()){
          if(i%100 == 99){
            Thread.sleep(120000)
              println("Wait a few seconds...")
          }
         matchList.add(runBlocking {
            matchIDList?.get(i)?.let { MatchApi.fetchMatch(apiService, it, apiKey) }!!
         })
      }
   }

   var gradeSum = 0.0

    matchList.forEach { match ->
       //get participant with matching puuid from account variable
         val participant = match.info.participants?.find { it.puuid == account?.puuid }

         if (participant != null) {
             val performanceGrade = PerformanceRunner.runPerformanceAnalysis(participant)
             gradeSum += performanceGrade.averageGrade
             performanceGrade.gamemode = match.info.gameMode.toString()
            println(performanceGrade)
         }
    }

    val averageGrade = gradeSum / matchList.size
    val  finalGradePercentage = 50+(averageGrade*25).round(1)
    println("Average sigma points: $finalGradePercentage")
}





