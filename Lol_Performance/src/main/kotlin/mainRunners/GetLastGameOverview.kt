package org.example.mainRunners

import kotlinx.coroutines.runBlocking
import no.stelar7.api.r4j.pojo.shared.RiotAccount
import org.example.ApiService
import org.example.api.AccountApi
import org.example.api.MatchApi
import org.example.performance.PerformanceRunner
import org.example.util.match.LOLMatchResponse

class GetLastGameOverview {
    fun getLastGameOverview(apiKey: String, apiService: ApiService) {

        println("Insert name and tag")
        val nameAndTag = readlnOrNull()
        println("How many games ago?")
        val gameIndex = readlnOrNull()?.toInt()

        val values = nameAndTag?.split("#")

        val account: RiotAccount? = runBlocking {
            values?.let { AccountApi.fetchPUUID(apiService, it[0], it[1], apiKey) }
        }

        val matchIDList: List<String>? = runBlocking {
            account?.let { MatchApi.fetchMatches(apiService, account.puuid, apiKey) }
        }

        val matchList: MutableList<LOLMatchResponse> = mutableListOf()

        if (gameIndex != null) {
            for (i in 0 until gameIndex){
                if(i%100 == 99){
                    Thread.sleep(120000)
                    println("Wait a few seconds...")
                }
                matchList.add(runBlocking {
                    matchIDList?.get(i)?.let { MatchApi.fetchMatch(apiService, it, apiKey) }!!
                })
            }
        }

        println("\n")
        println("\n")
        println(matchList[gameIndex!!-1].info)

        matchList[gameIndex-1].info.participants?.forEachIndexed { index, participant ->


            val firstTeamWon = matchList[gameIndex-1].info.teams?.get(0)?.didWin()

            if(index == 0){
                println("\n")
                firstTeamWon?.let {
                    if(it){
                        println("Team 1 (Winner)")
                    }
                    else{
                        println("Team 1")
                    }
                }
            }
            if(index == 5){
                println("\n")
                firstTeamWon?.let {
                    if(it){
                        println("Team 2")
                    }
                    else{
                        println("Team 2 (Winner)")
                    }
                }
            }
            val performanceGrade = PerformanceRunner.runPerformanceAnalysis(participant)
            performanceGrade.gamemode = matchList[gameIndex-1].info.gameMode.toString()
            println(performanceGrade.toStringShort())
        }

        println("\n")
        println("\n")
    }
}