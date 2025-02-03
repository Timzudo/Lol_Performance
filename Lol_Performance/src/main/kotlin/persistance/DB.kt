package org.example.persistance

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant
import java.io.File

class DB {

    companion object{
        private const val FILE_PATH = "db.json"
        
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
    }




}