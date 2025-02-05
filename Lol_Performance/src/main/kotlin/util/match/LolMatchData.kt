package org.example.util.match

import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard
import no.stelar7.api.r4j.basic.constants.types.lol.GameModeType
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType
import no.stelar7.api.r4j.basic.constants.types.lol.GameType
import no.stelar7.api.r4j.basic.constants.types.lol.MapType
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchTeam

data class LOLMatchData(
    val endOfGameResult: String? = null,
    val gameCreation: Long = 0,
    val gameDuration: Int = 0,
    val gameId: Long = 0,
    val gameMode: GameModeType? = null,
    val gameName: String? = null,
    val gameStartTimestamp: Long? = null,
    val gameEndTimestamp: Long? = null,
    val gameType: GameType? = null,
    val gameVersion: String? = null,
    val mapId: MapType? = null,
    val participants: List<MatchParticipant>? = null,
    val platformId: LeagueShard? = null,
    val queueId: GameQueueType? = null,
    val teams: List<MatchTeam>? = null,
    val tournamentCode: String? = null
){
    override fun toString(): String {
        return """
            -------------------------
            Duration: ${gameDuration.div(60)} min
            Mode: $gameMode
            -------------------------
        """.trimIndent()
    }
}