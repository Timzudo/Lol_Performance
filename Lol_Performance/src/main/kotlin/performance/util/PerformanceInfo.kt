package org.example.performance.util

data class PerformanceInfo(
    val damageDealtToObjectives: Double = 0.0,
    val damageSelfMitigated: Double = 0.0,
    val deaths: Double = 0.0,
    val assists: Double = 0.0,
    val goldEarned: Double = 0.0,
    val kills: Double = 0.0,
    val timeCCingOthers: Double = 0.0,
    val totalDamageDealtToChampions: Double = 0.0,
    val totalHeal: Double = 0.0,
    val totalMinionsKilled: Double = 0.0,
    val visionScore: Double = 0.0
)

