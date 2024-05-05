package com.example.hackathone.calcs

import android.util.Log

fun calcDosage(
    tone: Int,
    gender: Int,
    time: Float,
    age: Int
): Double {
    val absorptionCoefficients = mapOf(
        1 to 0.8,
        2 to 0.75,
        3 to 0.7,
        4 to 0.6,
        5 to 0.5,
        6 to 0.4
    )

    val genders = mapOf(
        1 to 1.6,  // муж
        2 to 1.3   // жен
    )

    val ageCoeff = mapOf(
        1 to 0.5,  // до 60
        2 to 0.25  // после 60
    )

    val ageCoeffValue = ageCoeff[if (age < 60) 1 else 2] ?: return 0.0
    if (absorptionCoefficients[tone]!! == null) {
        Log.d("absorptionCoefficients[tone]!!", "null")
    } else if (ageCoeffValue == null) {
        Log.d("ageCoeffValue", "null")
    } else if (time == null) {
        Log.d("time", "null")
    }

    // absorptionCoefficients[tone]!! * genders[gender]!! * 15 * 0.6 * time * ageCoeffValue * 0.1

    return absorptionCoefficients[tone]!! * 15 * 0.6 * time * ageCoeffValue * 0.1
}