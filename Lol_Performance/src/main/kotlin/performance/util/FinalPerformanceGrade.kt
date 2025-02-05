package org.example.performance.util

import org.example.util.match.round

data class FinalPerformanceGrade (
    val overallPerformanceGrade: PerformanceGrade,
    val championPerformanceGrade: PerformanceGrade,
    val overallGrade: Double,
    val championGrade: Double,
    val averageGrade: Double,
    val lane: String,
    val overallGradePercentage: Double,
    val championGradePercentage: Double,
    val averageGradePercentage: Double,
    var champion: String = "",
    var averageDamageDealtToObjectives: Double = 0.0,
    var averageDamageSelfMitigated: Double = 0.0,
    var averageDeaths: Double = 0.0,
    var averageAssists: Double = 0.0,
    var averageGoldEarned: Double = 0.0,
    var averageKills: Double = 0.0,
    var averageTimeCCingOthers: Double = 0.0,
    var averageTotalDamageDealtToChampions: Double = 0.0,
    var averageTotalHeal: Double = 0.0,
    var averageTotalMinionsKilled: Double = 0.0,
    var averageVisionScore: Double = 0.0,
    var textGrade: TextGrade = TextGrade.ERROR,
    var gamemode: String = "",
    var playerName: String = ""
) {
    override fun toString(): String {
        return """
            -------------------------
            $playerName
            Champion: $champion
            Lane: $lane
            Game Mode: $gamemode
            -----------------
            Kills: $averageKills
            Assists: $averageAssists
            Deaths: $averageDeaths
            Damage To Champions: $averageTotalDamageDealtToChampions
            Damage To Objectives: $averageDamageDealtToObjectives
            Self Mitigated Damage: $averageDamageSelfMitigated
            Gold Earned: $averageGoldEarned
            Minions Killed: $averageTotalMinionsKilled
            Time CCing Others: $averageTimeCCingOthers
            Total Heal: $averageTotalHeal
            Vision Score: $averageVisionScore
            -----------------
            Overall Sigma Points: $overallGradePercentage
            Champion Sigma Points: $championGradePercentage
            Final Sigma Points: $averageGradePercentage
            Sigma Grade: ${textGrade.description}
            -------------------------
        """.trimIndent()
    }

    fun toStringLong(): String {
        return """
            -------------------------
            Champion: $champion
            Final Performance Grade: $averageGrade
            Overall Performance Grade: $overallPerformanceGrade
            Champion Performance Grade: $championPerformanceGrade
            Overall Grade: $overallGrade
            Champion Grade: $championGrade
            Lane: $lane
            -----------------
            Average Damage Dealt To Objectives: $averageDamageDealtToObjectives
            Average Damage Self Mitigated: $averageDamageSelfMitigated
            Average Deaths: $averageDeaths
            Average Assists: $averageAssists
            Average Gold Earned: $averageGoldEarned
            Average Kills: $averageKills
            Average Time CCing Others: $averageTimeCCingOthers
            Average Total Damage Dealt To Champions: $averageTotalDamageDealtToChampions
            Average Total Heal: $averageTotalHeal
            Average Total Minions Killed: $averageTotalMinionsKilled
            Average Vision Score: $averageVisionScore
            -----------------
            Overall Grade Percentage: $overallGradePercentage
            Champion Grade Percentage: $championGradePercentage
            Sigma Points: $averageGradePercentage
            -------------------------
        """.trimIndent()
    }

    fun toStringShort(): String {
        return """
            -------------------------
            $playerName
            Champion: $champion
            Lane: $lane
            -----------------
            Final Sigma Points: $averageGradePercentage
            Sigma Grade: ${textGrade.description}
            -------------------------
        """.trimIndent()
    }
}