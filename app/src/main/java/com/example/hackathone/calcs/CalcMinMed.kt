fun calculateMed(solarPower: Float, skinTypes: Map<Int, Float>, skinType: Int): Float {
    // Расчет индекса УФ на основе силы солнца
    // Этот расчет является приблизительным и может отличаться в зависимости от конкретных условий
    if (solarPower == 0f) {
        return 0f
    }
    val uvIndex = solarPower * 10

    // Расчет MED по формуле
    val med = 1 / (uvIndex * 0.0001 * skinTypes[skinType]!!) * 2

    // Конвертация MED из часов в минуты
    val medInMinutes = med * 60

    return medInMinutes.toInt().toFloat()
}

// Пример использования функции
fun calcMed(
    skinType: Int,
    solarPower: Float
): Float {
    val skinTypes = mapOf(
        1 to 1.0f,
        2 to 1.3f,
        3 to 1.7f,
        4 to 2.0f,
        5 to 2.7f,
        6 to 3.3f
    )

    return calculateMed(solarPower, skinTypes, skinType)
}