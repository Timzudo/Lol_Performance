package org.example.mainRunners

import kotlinx.coroutines.runBlocking
import no.stelar7.api.r4j.pojo.shared.RiotAccount
import org.example.ApiService
import org.example.api.AccountApi
import org.example.api.MatchApi
import org.example.performance.PerformanceRunner
import org.example.util.match.LOLMatchResponse
import org.example.util.match.round

class GetPlayerGamesPerformance {

    fun getPlayerGamesPerformance(apiKey: String, apiService: ApiService) {


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
                println("\n")
                println(performanceGrade)
            }
        }

        val averageGrade = gradeSum / matchList.size
        val  finalGradePercentage = 50+(averageGrade*25).round(1)
        println("Average sigma points: $finalGradePercentage")
        println("\n")
        println("\n")
    }
}