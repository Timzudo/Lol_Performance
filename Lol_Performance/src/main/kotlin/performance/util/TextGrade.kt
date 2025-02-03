package org.example.performance.util

enum class TextGrade(val range: IntRange?, val description: String) {
    GRADE_0(null, "What the sigma \uD83D\uDE2D"),
    GRADE_1(10..19, "Horrible"),
    GRADE_2(20..29, "Bad-"),
    GRADE_3(30..39, "Bad"),
    GRADE_4(40..49, "Average-"),
    GRADE_5(50..59, "Average+"),
    GRADE_6(60..69, "Good"),
    GRADE_7(70..79, "Good+"),
    GRADE_8(80..89, "Excellent"),
    GRADE_9(null, "Certified Sigma \uD83E\uDD2B\uD83E\uDDCF\u200D♂\uFE0F"),
    ERROR(null, "ERROR");

    companion object {
        fun fromScore(score: Double): TextGrade {
            val intScore = score.toInt() // Convert Double to Int
            return when {
                intScore < 10 -> GRADE_1
                intScore > 90 -> GRADE_9
                else -> values().find { it.range?.contains(intScore) == true } ?: ERROR
            }
        }
    }
}
