package org.example.infoFetcher

import kotlinx.coroutines.runBlocking
import no.stelar7.api.r4j.basic.constants.types.lol.GameModeType
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant
import org.example.ApiService
import org.example.RetrofitClient
import org.example.api.MatchApi.Companion.fetchMatch
import org.example.api.MatchApi.Companion.fetchMatches
import org.example.persistance.DB

fun main() {
    val apiKey = "RGAPI-d30d8177-73ce-4032-a865-848608ec8e7d"
    val apiService = RetrofitClient.instance

    val PUUIDS = listOf(
        "vqWcyirqwe92aVB5amQul7jVof9gBasX9PgZILRyCp0vZ0bSQw9PWlZcJXyTgr5IGnUlzs0UZy77bw",
        "1YuU6_K0OcqeCkPlcGajSK_O3ScktEEQPmLic9i9B88VwRNln2RTCTYd33Tr10vNt2fmbN7vluldzA",
        "0R5EhbThtRM4KbPCR-o6Jkm4x4eMuFqoHioB5j6rA1kXai9r5WpY-E3pRI84kWohS93gFNOAGOSStg",
        "Z0M9wN_EkO9FeoVTmC5VSOngQPEVjyJXeEPkUPcBcqwlTTTQfNQZXJ61CTjLxg3Kw2xb5-eLwaudOQ",
        "K48DYgHGw1mLj4s4Jvhlw92ClfDtcpjY33ZyH6tJtvQFnOTaByhQgAajmiAYieOswyaF2SvwwLe5Aw",
        "ibVJ8Yeo55ylOMl1QUbumJKE4IdlxHyTnxl70JThe8LSuY6dYLM29k7P6aiITj_4WQgjRlV2sFLreA")

    val allMatches = mutableListOf<String>()

    PUUIDS.forEach { puuid ->
        allMatches.addAll(InfoFetcher.getAllmatchesUntillNull(apiService, puuid, apiKey))
    }

    val matchParticipationList = mutableListOf<MatchParticipant>()

    println("Match List Size: ${allMatches.size}")
    println("Match Participations: ${allMatches.size*10}")
    var counter = 0
    var matchCounter = 0

    allMatches.forEach { matchId ->
        val match = runBlocking { fetchMatch(apiService, matchId, apiKey) }
        Thread.sleep(1000)
        matchCounter++
        if (match != null) {
            if(match.info.gameMode == GameModeType.CLASSIC){
                match.info.participants?.forEach { participant ->
                    matchParticipationList.add(participant)
                    counter++
                    println("Counter: $counter")
                }
                matchCounter++
            }
            else{
                println("Match is not classic")
            }
        }
        else{
            println("Match is null")
        }
        println("-----------------------")
    }



    val filteredMatchList: Map<String, List<MatchParticipant>> = InfoFetcher.filterMatchParticipationsByChampion(matchParticipationList)

    DB.writeObjectToFile(filteredMatchList)

}

class InfoFetcher {

    companion object{
        fun filterMatchParticipationsByChampion(matches: List<MatchParticipant>): Map<String, List<MatchParticipant>> {
            return matches.groupBy { it.championName.toString() }
        }

        fun getAllmatchesUntillNull(apiService: ApiService, puuid: String, apiKey: String): List<String> {
            val matches = mutableListOf<String>()
            var start = 0
            val count = 100
            Thread.sleep(1000)
            var matchList = runBlocking { fetchMatches(apiService, puuid, apiKey, count, start) }
            while (matchList?.size!! > 0) {
                matches.addAll(matchList)
                start += count
                Thread.sleep(1000)
                matchList = runBlocking { fetchMatches(apiService, puuid, apiKey, count, start) }
                println("Match List Size: ${matches.size}")
            }
            return matches
        }
    }
}