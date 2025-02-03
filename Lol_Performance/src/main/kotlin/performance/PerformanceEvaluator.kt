package org.example.performance

import org.example.performance.util.FinalPerformanceGrade
import org.example.performance.util.PerformanceGrade
import org.example.performance.util.PerformanceInfo
import org.example.performance.util.TextGrade
import org.example.util.match.round
import kotlin.math.sqrt

class PerformanceEvaluator {

    companion object{
        //weights
        private val damageDealtToObjectivesWeight = 0.2
        private val damageSelfMitigatedWeight = 0.1
        private val deathsWeight = -0.4
        private val assistsWeight = 0.3
        private val killsWeight = 0.3
        private val timeCCingOthersWeight = 0.05
        private val totalDamageDealtToChampionsWeight = 0.3
        private val totalHealWeight = 0.15
        private val visionScoreWeight = 0.15

        //not for support role
        private val goldEarnedWeight = 0.2
        private val killsWeightSupport = 0.1

        //not for support nor jungle role
        private val totalMinionsKilledWeight = 0.05


        fun evaluatePerformance(performanceInfo: PerformanceInfo, overallPerformanceInfo: List<PerformanceInfo>, championPerformanceInfo: List<PerformanceInfo>, lane: String): FinalPerformanceGrade {
            val damageDealtToObjectives = getGrade(performanceInfo.damageDealtToObjectives, overallPerformanceInfo.map { it.damageDealtToObjectives }, championPerformanceInfo.map { it.damageDealtToObjectives })
            val damageSelfMitigated = getGrade(performanceInfo.damageSelfMitigated, overallPerformanceInfo.map { it.damageSelfMitigated }, championPerformanceInfo.map { it.damageSelfMitigated })
            val deaths = getGrade(performanceInfo.deaths, overallPerformanceInfo.map { it.deaths }, championPerformanceInfo.map { it.deaths })
            val assists = getGrade(performanceInfo.assists, overallPerformanceInfo.map { it.assists }, championPerformanceInfo.map { it.assists })
            val goldEarned = getGrade(performanceInfo.goldEarned, overallPerformanceInfo.map { it.goldEarned }, championPerformanceInfo.map { it.goldEarned })
            val kills = getGrade(performanceInfo.kills, overallPerformanceInfo.map { it.kills }, championPerformanceInfo.map { it.kills })
            val timeCCingOthers = getGrade(performanceInfo.timeCCingOthers, overallPerformanceInfo.map { it.timeCCingOthers }, championPerformanceInfo.map { it.timeCCingOthers })
            val totalDamageDealtToChampions = getGrade(performanceInfo.totalDamageDealtToChampions, overallPerformanceInfo.map { it.totalDamageDealtToChampions }, championPerformanceInfo.map { it.totalDamageDealtToChampions })
            val totalHeal = getGrade(performanceInfo.totalHeal, overallPerformanceInfo.map { it.totalHeal }, championPerformanceInfo.map { it.totalHeal })
            val totalMinionsKilled = getGrade(performanceInfo.totalMinionsKilled, overallPerformanceInfo.map { it.totalMinionsKilled }, championPerformanceInfo.map { it.totalMinionsKilled })
            val visionScore = getGrade(performanceInfo.visionScore, overallPerformanceInfo.map { it.visionScore }, championPerformanceInfo.map { it.visionScore })

            val overallPerformanceGrade = PerformanceGrade(
                damageDealtToObjectives.first,
                damageSelfMitigated.first,
                deaths.first,
                assists.first,
                goldEarned.first,
                kills.first,
                timeCCingOthers.first,
                totalDamageDealtToChampions.first,
                totalHeal.first,
                totalMinionsKilled.first,
                visionScore.first
            )

            val championPerformanceGrade = PerformanceGrade(
                damageDealtToObjectives.second,
                damageSelfMitigated.second,
                deaths.second,
                assists.second,
                goldEarned.second,
                kills.second,
                timeCCingOthers.second,
                totalDamageDealtToChampions.second,
                totalHeal.second,
                totalMinionsKilled.second,
                visionScore.second
            )

            val overallGrade = getFinalGradeValue(overallPerformanceGrade, lane)
            val championGrade = getFinalGradeValue(championPerformanceGrade, lane)
            val averageGrade = (overallGrade + championGrade) / 2
            val overallGradePercentage = (50+(overallGrade*25)).round(1)
            val championGradePercentage = (50+(championGrade*25)).round(1)
            val averageGradePercentage = (50+(averageGrade*25)).round(1)

            var averageDamageDealtToObjectives = damageDealtToObjectives.first + damageDealtToObjectives.second / 2
            var averageDamageSelfMitigated = damageSelfMitigated.first + damageSelfMitigated.second / 2
            var averageDeaths = deaths.first + deaths.second / 2
            var averageAssists = assists.first + assists.second / 2
            var averageGoldEarned = goldEarned.first + goldEarned.second / 2
            var averageKills = kills.first + kills.second / 2
            var averageTimeCCingOthers = timeCCingOthers.first + timeCCingOthers.second / 2
            var averageTotalDamageDealtToChampions = totalDamageDealtToChampions.first + totalDamageDealtToChampions.second / 2
            var averageTotalHeal = totalHeal.first + totalHeal.second / 2
            var averageTotalMinionsKilled = totalMinionsKilled.first + totalMinionsKilled.second / 2
            var averageVisionScore = visionScore.first + visionScore.second / 2

            averageDamageDealtToObjectives = (50+(averageDamageDealtToObjectives*25)).round(1).round(1)
            averageDamageSelfMitigated = (50+(averageDamageSelfMitigated*25)).round(1).round(1)
            averageDeaths = (50+(averageDeaths*25)).round(1).round(1)
            averageAssists = (50+(averageAssists*25)).round(1).round(1)
            averageGoldEarned = (50+(averageGoldEarned*25)).round(1).round(1)
            averageKills = (50+(averageKills*25)).round(1).round(1)
            averageTimeCCingOthers = (50+(averageTimeCCingOthers*25)).round(1).round(1)
            averageTotalDamageDealtToChampions = (50+(averageTotalDamageDealtToChampions*25)).round(1).round(1)
            averageTotalHeal = (50+(averageTotalHeal*25)).round(1).round(1)
            averageTotalMinionsKilled = (50+(averageTotalMinionsKilled*25)).round(1).round(1)
            averageVisionScore = (50+(averageVisionScore*25)).round(1).round(1)

            val textGrade = TextGrade.fromScore(averageGradePercentage)

            return FinalPerformanceGrade(overallPerformanceGrade, championPerformanceGrade, overallGrade, championGrade, averageGrade, lane, overallGradePercentage, championGradePercentage, averageGradePercentage, "", averageDamageDealtToObjectives, averageDamageSelfMitigated, averageDeaths, averageAssists, averageGoldEarned, averageKills, averageTimeCCingOthers, averageTotalDamageDealtToChampions, averageTotalHeal, averageTotalMinionsKilled, averageVisionScore, textGrade)

        }

        private fun getFinalGradeValue(performanceGrade: PerformanceGrade, lane: String): Double{

            if (lane == "SUPPORT")
                return (performanceGrade.damageDealtToObjectives * damageDealtToObjectivesWeight +
                        performanceGrade.damageSelfMitigated * damageSelfMitigatedWeight +
                        performanceGrade.deaths * deathsWeight + performanceGrade.assists * assistsWeight + performanceGrade.kills * killsWeightSupport +
                        performanceGrade.timeCCingOthers * timeCCingOthersWeight + performanceGrade.totalDamageDealtToChampions * totalDamageDealtToChampionsWeight +
                        performanceGrade.totalHeal * totalHealWeight +
                        performanceGrade.visionScore * visionScoreWeight)
                    .div(damageDealtToObjectivesWeight + damageSelfMitigatedWeight +
                            deathsWeight + assistsWeight + killsWeightSupport + timeCCingOthersWeight
                            + totalDamageDealtToChampionsWeight + totalHealWeight
                            + visionScoreWeight)

            if(lane == "JUNGLE")
                return (performanceGrade.damageDealtToObjectives * damageDealtToObjectivesWeight +
                        performanceGrade.damageSelfMitigated * damageSelfMitigatedWeight + performanceGrade.goldEarned * goldEarnedWeight +
                        performanceGrade.deaths * deathsWeight + performanceGrade.assists * assistsWeight + performanceGrade.kills * killsWeight +
                        performanceGrade.timeCCingOthers * timeCCingOthersWeight + performanceGrade.totalDamageDealtToChampions * totalDamageDealtToChampionsWeight +
                        performanceGrade.totalHeal * totalHealWeight +
                        performanceGrade.visionScore * visionScoreWeight)
                    .div(damageDealtToObjectivesWeight + damageSelfMitigatedWeight +
                            deathsWeight + assistsWeight + killsWeight + timeCCingOthersWeight
                            + totalDamageDealtToChampionsWeight + totalHealWeight
                            + visionScoreWeight + goldEarnedWeight)

            return (performanceGrade.damageDealtToObjectives * damageDealtToObjectivesWeight +
                    performanceGrade.damageSelfMitigated * damageSelfMitigatedWeight + performanceGrade.goldEarned * goldEarnedWeight +
                    performanceGrade.deaths * deathsWeight + performanceGrade.assists * assistsWeight + performanceGrade.kills * killsWeight +
                    performanceGrade.timeCCingOthers * timeCCingOthersWeight + performanceGrade.totalDamageDealtToChampions * totalDamageDealtToChampionsWeight +
                    performanceGrade.totalHeal * totalHealWeight + performanceGrade.totalMinionsKilled * totalMinionsKilledWeight +
                    performanceGrade.visionScore * visionScoreWeight)
                .div(damageDealtToObjectivesWeight + damageSelfMitigatedWeight +
                        deathsWeight + assistsWeight + killsWeight + timeCCingOthersWeight
                        + totalDamageDealtToChampionsWeight + totalHealWeight + totalMinionsKilledWeight
                        + visionScoreWeight + goldEarnedWeight)
        }

        private fun getGrade(value: Double, overall: List<Double>, champion: List<Double>): Pair<Double, Double> {
            val overallMean = overall.average()
            val overallStdDev = overall.standardDeviation()
            val championMean = champion.average()
            val championStdDev = champion.standardDeviation()

            val zOverall = (value - overallMean) / overallStdDev
            val zChampion = (value - championMean) / championStdDev

            return  Pair(zOverall, zChampion)
        }

        // Function to calculate standard deviation
        private fun List<Double>.standardDeviation(): Double {
            val mean = this.average()
            return sqrt(this.map { (it - mean) * (it - mean) }.average())
        }
    }
}