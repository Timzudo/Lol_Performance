package org.example.persistance

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant
import org.example.performance.PerformanceRunner.Companion.getAllMatchParticipationsFromChampion
import org.example.performance.PerformanceRunner.Companion.getChampionPerformanceInfoList
import org.example.performance.PerformanceRunner.Companion.getOverallPerformanceInfoList
import org.example.performance.util.PerformanceInfo
import java.io.File

class DB {

    companion object{
        private const val FILE_PATH = "db.json"
        private const val OVERALL_PATH = "overall_performance_info.json"
        private const val CHAMPION_PATH = "champion_performance_info.json"
        
        fun writeObjectToFile(listMatchParticipations: Map<String, List<MatchParticipant>>) {
            val gson = Gson()
            val jsonString = gson.toJson(listMatchParticipations) // Convert object to JSON string
            File(FILE_PATH).writeText(jsonString)
        }
        
        fun readObjectFromFile(): Map<String, List<MatchParticipant>> {
            val gson = Gson()
            val jsonString = File(FILE_PATH).readText()
            val type = object : TypeToken<Map<String, List<MatchParticipant>>>() {}.type
            return gson.fromJson(jsonString, type)
        }

        fun createPerformanceDB(){
            val db = readObjectFromFile()

            val overallPerformanceDB = getOverallPerformanceInfoList(db)

            val championNames = db.values.flatten().map { it.championName }.distinct()

            val championPerformanceDB = mutableMapOf<String, List<PerformanceInfo>>()

            championNames.forEach { championName ->
                championPerformanceDB[championName] = getChampionPerformanceInfoList(db, championName)
            }

            val gson = Gson()
            val overallJson = gson.toJson(overallPerformanceDB) // Convert object to JSON string
            File(OVERALL_PATH).writeText(overallJson)

            val championJson = gson.toJson(championPerformanceDB) // Convert object to JSON string
            File(CHAMPION_PATH).writeText(championJson)
        }

        fun readOverallPerformanceDB(): List<PerformanceInfo> {
            val gson = Gson()
            val jsonString = File(OVERALL_PATH).readText()
            val type = object : TypeToken<List<PerformanceInfo>>() {}.type
            return gson.fromJson(jsonString, type)
        }

        fun readChampionPerformanceDB(): List<PerformanceInfo> {
            val gson = Gson()
            val jsonString = File(CHAMPION_PATH).readText()
            val type = object : TypeToken<List<PerformanceInfo>>() {}.type
            return gson.fromJson(jsonString, type)
        }

        fun readChampionPerformanceDB(championName: String): List<PerformanceInfo> {
            val gson = Gson()
            val jsonString = File(CHAMPION_PATH).readText()
            val type = object : TypeToken<Map<String, List<PerformanceInfo>>>() {}.type
            val championPerformanceDB: Map<String, List<PerformanceInfo>> = gson.fromJson(jsonString, type)
            return championPerformanceDB[championName] ?: emptyList()
        }
    }
}