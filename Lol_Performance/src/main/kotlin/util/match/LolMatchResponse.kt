package org.example.util.match

import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMetadata
import java.io.Serializable

data class LOLMatchResponse(
    val info: LOLMatchData,
    val metadata: LOLMetadata
) : Serializable