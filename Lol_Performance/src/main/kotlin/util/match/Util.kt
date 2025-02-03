package org.example.util.match

import java.util.*

fun Double.round(decimals: Int): Double {
    //do it like this;Math.round(number * 1000.0) / 1000.0
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return Math.round(this * multiplier) / multiplier
}
