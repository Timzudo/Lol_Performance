package org.example.performance.util

enum class TextGrade(val range: IntRange?, val description: String) {
    GRADE_0(null, "\uD83D\uDFE5 What the sigma \uD83D\uDE2D"),
    GRADE_1(10..19, "\uD83D\uDFE5 Horrible"),
    GRADE_2(20..29, "\uD83D\uDFE7  Bad-"),
    GRADE_3(30..39, "\uD83D\uDFE7  Bad"),
    GRADE_4(40..49, "\uD83D\uDFE8 Average-"),
    GRADE_5(50..59, "\uD83D\uDFE8 Average+"),
    GRADE_6(60..69, "\uD83D\uDFE9 Good"),
    GRADE_7(70..79, "\uD83D\uDFE9 Good+"),
    GRADE_8(80..89, "\uD83D\uDFE6 Excellent"),
    GRADE_9(null, "\uD83D\uDFE6 Certified Sigma \uD83E\uDD2B\uD83E\uDDCF\u200D♂\uFE0F"),
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
