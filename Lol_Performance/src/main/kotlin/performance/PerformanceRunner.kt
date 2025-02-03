package org.example.performance

import kotlinx.coroutines.runBlocking
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant
import org.example.ApiService
import org.example.api.MatchApi
import org.example.performance.util.FinalPerformanceGrade
import org.example.performance.util.PerformanceInfo
import org.example.persistance.DB

class PerformanceRunner {

    companion object{

        val supportItemIdSet = setOf(3865, 3866, 3867, 3869, 3870, 3871, 3876, 3877)

        fun runPerformanceAnalysis(match: MatchParticipant): FinalPerformanceGrade{


            val overallPerformanceInfoList = DB.readOverallPerformanceDB()
            val championPerformanceInfoList = DB.readChampionPerformanceDB(match.championName)

            val performanceInfo = PerformanceInfo(
                damageDealtToObjectives = match.damageDealtToObjectives.div(match.timePlayed.toDouble()/60),
                damageSelfMitigated = match.damageSelfMitigated.div(match.timePlayed.toDouble()/60),
                deaths = match.deaths.div(match.timePlayed.toDouble()/60),
                assists = match.assists.div(match.timePlayed.toDouble()/60),
                goldEarned = match.goldEarned.div(match.timePlayed.toDouble()/60),
                kills = match.kills.div(match.timePlayed.toDouble()/60),
                timeCCingOthers = match.timeCCingOthers.div(match.timePlayed.toDouble()/60),
                totalDamageDealtToChampions = match.totalDamageDealtToChampions.div(match.timePlayed.toDouble()/60),
                totalHeal = match.totalHeal.div(match.timePlayed.toDouble()/60),
                totalMinionsKilled = match.totalMinionsKilled.div(match.timePlayed.toDouble()/60),
                visionScore = match.visionScore.div(match.timePlayed.toDouble()/60)
            )

            var lane = "LANE"

            if(isJungle(match)){
                lane = "JUNGLE"
            }
            if (hasSupportItem(match)){
                lane = "SUPPORT"
            }



            val finalPerformance = PerformanceEvaluator.evaluatePerformance(performanceInfo, overallPerformanceInfoList, championPerformanceInfoList, lane)

            finalPerformance.champion = match.championName
            return finalPerformance
        }

        fun getChampionPerformanceInfoList(matchList:  Map<String, List<MatchParticipant>>, championName: String): List<PerformanceInfo>{

            val championMatchParticipations = getAllMatchParticipationsFromChampion(championName, matchList)


            val championPerformanceInfoList = championMatchParticipations.map { match ->
                PerformanceInfo(
                    damageDealtToObjectives = calculatePerMinute(match) { it.damageDealtToObjectives },
                    damageSelfMitigated = calculatePerMinute(match) { it.damageSelfMitigated },
                    deaths = calculatePerMinute(match) { it.deaths },
                    assists = calculatePerMinute(match) { it.assists },
                    goldEarned = calculatePerMinute(match) { it.goldEarned },
                    kills = calculatePerMinute(match) { it.kills },
                    timeCCingOthers = calculatePerMinute(match) { it.timeCCingOthers },
                    totalDamageDealtToChampions = calculatePerMinute(match) { it.totalDamageDealtToChampions },
                    totalHeal = calculatePerMinute(match) { it.totalHeal },
                    totalMinionsKilled = calculatePerMinute(match) { it.totalMinionsKilled },
                    visionScore = calculatePerMinute(match) { it.visionScore }
                )
            }

            return championPerformanceInfoList
        }

        fun getOverallPerformanceInfoList(matchList:  Map<String, List<MatchParticipant>>): List<PerformanceInfo>{

            val overallMatchParticipations = getAllMatchParticipations(matchList)


            val overallPerformanceInfoList = overallMatchParticipations.map { match ->
                PerformanceInfo(
                    damageDealtToObjectives = calculatePerMinute(match) { it.damageDealtToObjectives },
                    damageSelfMitigated = calculatePerMinute(match) { it.damageSelfMitigated },
                    deaths = calculatePerMinute(match) { it.deaths },
                    assists = calculatePerMinute(match) { it.assists },
                    goldEarned = calculatePerMinute(match) { it.goldEarned },
                    kills = calculatePerMinute(match) { it.kills },
                    timeCCingOthers = calculatePerMinute(match) { it.timeCCingOthers },
                    totalDamageDealtToChampions = calculatePerMinute(match) { it.totalDamageDealtToChampions },
                    totalHeal = calculatePerMinute(match) { it.totalHeal },
                    totalMinionsKilled = calculatePerMinute(match) { it.totalMinionsKilled },
                    visionScore = calculatePerMinute(match) { it.visionScore }
                )
            }


            return overallPerformanceInfoList
        }

        private fun hasSupportItem(match: MatchParticipant): Boolean{
            return match.item0 in supportItemIdSet || match.item1 in supportItemIdSet || match.item2 in supportItemIdSet || match.item3 in supportItemIdSet || match.item4 in supportItemIdSet || match.item5 in supportItemIdSet
        }

        private fun isJungle(match: MatchParticipant): Boolean{
            return match.summoner1Id == 11 || match.summoner2Id == 11
        }

        fun getAllMatchParticipationsFromChampion(championName: String, matches: Map<String, List<MatchParticipant>>): List<MatchParticipant> {
            val list = mutableListOf<MatchParticipant>()

            matches.forEach { (t, u) ->
                u.forEach { match ->
                    if(match.championName == championName){
                        list.add(match)
                    }
                }
            }

            return list
        }

        private fun getAllMatchParticipations(matches: Map<String, List<MatchParticipant>>): List<MatchParticipant> {
            return matches.flatMap { it.value }
        }

        // Helper function to calculate per minute for each game
        private fun calculatePerMinute(match: MatchParticipant, statExtractor: (MatchParticipant) -> Int): Double {
            val stat = statExtractor(match).toDouble() // Convert stat to Double
            val minutes = match.timePlayed / 60.0 // Convert seconds to minutes as Double
            return if (minutes > 0) {
                stat / minutes // Maintain floating-point precision
            } else {
                0.0 // Avoid division by zero
            }
        }
    }
}